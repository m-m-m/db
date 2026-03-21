ALTER TABLE Product ADD COLUMN Category INTEGER;
ALTER TABLE Product ADD CONSTRAINT FK_Product_Category FOREIGN KEY (Category) REFERENCES Category(Id);
UPDATE Product SET Category=1;
UPDATE Product SET Category=2 WHERE Title LIKE '%DVD%';
UPDATE Product SET Category=4 WHERE Title LIKE '%LP%';