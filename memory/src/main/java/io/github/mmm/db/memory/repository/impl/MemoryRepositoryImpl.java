package io.github.mmm.db.memory.repository.impl;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import io.github.mmm.base.collection.AbstractIterator;
import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.bean.BeanHelper;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexGetByName;
import io.github.mmm.db.ddl.index.DbIndexRegistry;
import io.github.mmm.db.memory.index.IndexOperation;
import io.github.mmm.db.memory.index.MemoryIndex;
import io.github.mmm.db.memory.repository.MemoryRepository;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.spi.AbstractEntityRepository;
import io.github.mmm.db.tx.spi.DbEntityHolder;
import io.github.mmm.db.tx.spi.DbEntitySession;
import io.github.mmm.db.tx.spi.DbEntitySessionFactory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.OptimisicLockException;
import io.github.mmm.entity.id.generator.IdGenerator;
import io.github.mmm.entity.id.generator.SequenceIdGenerator;
import io.github.mmm.entity.id.sequence.IdSequenceMemory;
import io.github.mmm.entity.link.IdLink;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredState;
import io.github.mmm.marshall.StructuredWriter;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.container.collection.CollectionProperty;
import io.github.mmm.property.container.collection.ReadableCollectionProperty;
import io.github.mmm.property.string.StringCollectionProperty;

/**
 * Abstract base class for an in-memory {@link EntityRepository}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 */
public class MemoryRepositoryImpl<E extends EntityBean> extends AbstractEntityRepository<E>
    implements MemoryRepository<E> {

  private final Function<Id<?>, EntityBean> resolver;

  private final DbEntitySession<E> entitySession;

  private final IdSequenceMemory sequence;

  private final IdGenerator idGenerator;

  private final FindAll findAll;

  private final DbIndexRegistryImpl indices;

  private MemoryRepository<E> proxy;

  /**
   * The constructor.
   *
   * @param prototype the {@link #getPrototype() prototype}.
   * @param proxy the dynamic proxy instance of this internal {@link MemoryRepository} acting as external instance.
   */
  public MemoryRepositoryImpl(E prototype, MemoryRepository<E> proxy) {

    this(prototype, proxy, null);
  }

  /**
   * The constructor.
   *
   * @param prototype the {@link #getPrototype() prototype}.
   * @param proxy the dynamic proxy instance of this internal {@link MemoryRepository} acting as external instance.
   * @param resolver the custom resolver {@link Function} for lazy loading or {@code null} to build default.
   */
  public MemoryRepositoryImpl(E prototype, MemoryRepository<E> proxy, Function<Id<?>, EntityBean> resolver) {

    super(prototype);
    if (resolver == null) {
      this.resolver = new RepoResolver();
    } else {
      this.resolver = resolver;
    }
    this.entitySession = DbEntitySessionFactory.get().create(prototype);
    this.sequence = new IdSequenceMemory();
    this.idGenerator = new SequenceIdGenerator(this.sequence);
    this.findAll = new FindAll();
    this.indices = new DbIndexRegistryImpl();
    this.proxy = proxy;
  }

  @Override
  public void init() {

    super.init();
    if (this.proxy != null) {
      this.proxy.createIndexes(this.indices);
      this.proxy = null;
    }
  }

  @Override
  protected boolean isUseRevision() {

    // pragmatic default for in memory
    return false;
  }

  @Override
  public E doFindById(Id<E> id) {

    if (id == null) {
      return null;
    }
    DbEntityHolder<E> holder = this.entitySession.get(id.withoutRevision());
    if (holder == null) {
      return null;
    }
    return holder.getExternal();
  }

  @Override
  public boolean doDeleteById(Id<E> id) {

    if (id == null) {
      return false;
    }
    DbEntityHolder<E> holder = this.entitySession.remove(id.withoutRevision());
    if (holder == null) {
      return false;
    } else {
      updateIndices(holder.getInternal(), null, IndexOperation.REMOVE);
      return true;
    }
  }

  @Override
  protected int doDeleteAllById(Iterable<Id<E>> ids) {

    int count = 0;
    for (Id<E> id : ids) {
      boolean deleted = deleteById(id);
      if (deleted) {
        count++;
      }
    }
    return count;
  }

  @Override
  public <I extends DbIndex> I getIndex(String name) {

    return this.indices.getIndex(name);
  }

  @SuppressWarnings({ "rawtypes", "unchecked", "null" })
  private void updateIndices(E entity, E oldEntity, IndexOperation operation) {

    for (MemoryIndex<?, ?> index : this.indices) {
      MemoryIndex rawIndex = index;
      for (DbColumnReferenceWithSortOrder column : index.getColumns()) {
        ReadableProperty<?> property = entity.getProperty(column.getProperty().getName());
        WritableProperty oldProperty = null;
        if (oldEntity != null) {
          oldProperty = oldEntity.getProperty(property.getName());
        }
        if (property != null) {
          if (property instanceof ReadableCollectionProperty<?, ?> c) {
            switch (operation) {
              case ADD -> rawIndex.addAll(c.get(), entity);
              case UPDATE -> rawIndex.updateAll(c.get(), entity,
                  ((ReadableCollectionProperty<?, ?>) oldProperty).get());
              case REMOVE -> rawIndex.removeAll(c.get(), entity);
            }
          } else if (property instanceof StringCollectionProperty c) {
            switch (operation) {
              case ADD -> rawIndex.addAll(c, entity);
              case UPDATE -> rawIndex.updateAll(c, entity, (StringCollectionProperty) oldProperty);
              case REMOVE -> rawIndex.removeAll(c, entity);
            }
          } else {
            Object newKey = property.get();
            switch (operation) {
              case ADD -> rawIndex.add(newKey, entity);
              case UPDATE -> rawIndex.update(newKey, entity, oldProperty.get());
              case REMOVE -> rawIndex.remove(newKey);
            }
          }
        }
      }
    }
  }

  @Override
  protected E doInsert(E entity) {

    return doInsert(entity, false);
  }

  private E doInsert(E entity, boolean internal) {

    DbEntityHolder<E> holder = this.entitySession.put(entity);
    connectResolver(holder.getExternal());
    updateIndices(entity, null, IndexOperation.ADD);
    return holder.getExternal();
  }

  @SuppressWarnings({ "unchecked" })
  private void connectResolver(E entity) {

    for (WritableProperty<?> property : entity.getProperties()) {
      if (!property.isReadOnly()) {
        if (property instanceof LinkProperty linkProperty) {
          linkProperty.setResolver(this.resolver);
        } else if (property instanceof CollectionProperty<?, ?> collectionProperty) {
          WritableProperty<?> valueProperty = collectionProperty.getValueProperty();
          if (valueProperty instanceof LinkProperty linkProperty) {
            linkProperty.setResolver(this.resolver);
            Collection<Link<?>> collection = (Collection<Link<?>>) collectionProperty.get();
            if (collection != null) {
              for (Link<?> link : collection) {
                if (link instanceof IdLink idLink) {
                  idLink.setResolver(this.resolver);
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  protected E doUpdate(E entity) {

    Id<E> id = Id.from(entity);
    DbEntityHolder<E> holder = this.entitySession.get(id);
    if (holder == null) {
      throw new IllegalArgumentException("Cannot save transient entity with persistent id " + id);
    }
    E internal = holder.getInternal();
    if (isUseRevision()) {
      Id<?> internalId = internal.getId();
      if (!internalId.equals(id)) {
        throw new OptimisicLockException(id, internal.getType().getStableName());
      }
      internalId = GenericId.updateRevision(internalId);
      entity.setId(internalId);
    }
    updateIndices(entity, internal, IndexOperation.UPDATE);
    BeanHelper.copy(entity, internal);
    return holder.getExternal();
  }

  @Override
  public Iterable<E> findAll() {

    return this.findAll;
  }

  @Override
  protected IdGenerator getIdGenerator() {

    return this.idGenerator;
  }

  /**
   * @return the {@link IdSequenceMemory}.
   */
  protected IdSequenceMemory getSequence() {

    return this.sequence;
  }

  /**
   * Loads an entire array of {@link EntityBean entities} from the given {@link StructuredReader} and insert them into
   * this repository.
   *
   * @param reader the {@link StructuredReader} to read the array of entities from.
   * @see #read(Path)
   */
  @Override
  public void read(StructuredReader reader) {

    reader.require(StructuredState.START_ARRAY, true);
    while (!reader.readEndArray()) {
      E entity = ReadableBean.newInstance(this.prototype);
      entity.read(reader);
      doInsert(entity, true);
    }
  }

  @Override
  public void write(StructuredWriter writer) {

    writer.writeStartArray();
    for (DbEntityHolder<E> holder : this.entitySession) {
      holder.getInternal().write(writer);
    }
    writer.writeEnd();
  }

  private class RepoResolver implements Function<Id<?>, EntityBean> {

    @SuppressWarnings("unchecked")
    @Override
    public EntityBean apply(Id<?> id) {

      if (id == null) {
        return null;
      }
      if (id.getEntityClass() == getEntityClass()) {
        return findById((Id<E>) id);
      }
      return null;
    }
  }

  private class DbIndexRegistryImpl implements DbIndexRegistry, DbIndexGetByName, Iterable<MemoryIndex<?, ?>> {

    private Map<String, DbIndex> name2IndexMap;

    private DbIndexRegistryImpl() {

      super();
    }

    @Override
    public void add(DbIndex index) {

      Objects.requireNonNull(index);
      assert (index instanceof MemoryIndex);
      if (this.name2IndexMap == null) {
        this.name2IndexMap = new HashMap<>();
      }
      String name = index.getName();
      DbIndex duplicate = this.name2IndexMap.put(name, index);
      if (duplicate != null) {
        throw new DuplicateObjectException(index, name, duplicate);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends DbIndex> I getIndex(String name) {

      DbIndex index = null;
      if (this.name2IndexMap != null) {
        index = this.name2IndexMap.get(name);
      }
      if (index == null) {
        throw new ObjectNotFoundException("MemoryIndex", name);
      }
      return (I) index;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Iterator<MemoryIndex<?, ?>> iterator() {

      if (this.name2IndexMap == null) {
        return Collections.emptyIterator();
      }
      return (Iterator) this.name2IndexMap.values().iterator();
    }
  }

  private class FindAll implements Iterable<E> {

    @Override
    public Iterator<E> iterator() {

      return new FindAllIterator<>(MemoryRepositoryImpl.this.entitySession.iterator());
    }
  }

  private static class FindAllIterator<E extends EntityBean> extends AbstractIterator<E> {

    private final Iterator<DbEntityHolder<E>> iterator;

    private FindAllIterator(Iterator<DbEntityHolder<E>> iterator) {

      super();
      this.iterator = iterator;
      findFirst();
    }

    @Override
    protected E findNext() {

      if (this.iterator.hasNext()) {
        DbEntityHolder<E> holder = this.iterator.next();
        return holder.getExternal();
      }
      return null;
    }

  }

}
