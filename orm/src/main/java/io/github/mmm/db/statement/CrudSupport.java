package io.github.mmm.db.statement;

import java.util.Collection;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.db.statement.delete.DeleteClause;
import io.github.mmm.db.statement.delete.DeleteStatement;
import io.github.mmm.db.statement.select.SelectEntityClause;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/// This class provides support for CRUD (Create, Retrieve, Update, Delete) operations.
///
/// @param <E> type of the [EntityBean].
public class CrudSupport<E extends EntityBean> {

  /// The constructor.
  public CrudSupport() {

    super();
  }

  /// @param id the [Id].
  /// @return the new [EntityBean] instance.
  protected E createBean(Id<E> id) {

    return BeanFactory.get().create(id.getEntityClass());
  }

  /// @param id the [Id] of the [EntityBean] to delete.
  /// @return the [DeleteStatement] to delete by the given [Id]s.
  public DeleteStatement<E> deleteById(Id<E> id) {

    if (id == null) {
      return null;
    }
    E entity = createBean(id);
    return new DeleteClause().from(entity).where(entity.Id().eq(id)).get();
  }

  /// @param ids the [Collection] with the [Id]s of the [EntityBean]s to delete.
  /// @return the [DeleteStatement] to delete by the given [Id]s.
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public DeleteStatement<E> deleteByIds(Collection<Id<E>> ids) {

    if ((ids == null) || ids.isEmpty()) {
      return null;
    }
    E entity = createBean(ids.iterator().next());
    return new DeleteClause().from(entity).where(entity.Id().in((Collection) ids)).get();
  }

  /// @param ids the [Collection] with the [Id]s of the [EntityBean]s to select.
  /// @return the [SelectStatement] to select from the given [Id]s.
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SelectStatement<E> selectByIds(Collection<Id<E>> ids) {

    if ((ids == null) || ids.isEmpty()) {
      return null;
    }
    E entity = createBean(ids.iterator().next());
    SelectEntityClause<E> select = new SelectEntityClause<>(entity);
    return select.from().where(entity.Id().in((Collection) ids)).get();
  }

  /// @param id the [Id] of the [EntityBean] to select.
  /// @return the [SelectStatement] to select from the given [Id].
  public SelectStatement<E> selectById(Id<E> id) {

    if (id == null) {
      return null;
    }
    E entity = createBean(id);
    SelectEntityClause<E> select = new SelectEntityClause<>(entity);
    return select.from().where(entity.Id().eq(id)).get();
  }

}
