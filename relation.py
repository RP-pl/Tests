import sqlalchemy as sqla
from sqlalchemy import create_engine
from sqlalchemy.exc import InvalidRequestError, IntegrityError
from sqlalchemy.orm import relationship
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
password=""
engine = create_engine("mysql+mysqlconnector://root:"+password+"@localhost:3306/relationdb")

Base = declarative_base()

class Parent(Base):
    __tablename__ = "Parent"
    id = sqla.Column(sqla.INTEGER,autoincrement=True,primary_key=True)
    name = sqla.Column(sqla.String(length=50))
    #children = ralationship('Child') one-way
    #Bidirectional
    children = relationship('Child',back_populates = "parent")
    def __str__(self):
        s ="Parent{id="+str(self.id)+",children=["
        for child in self.children:
            s+= str(child)+","
        s+="}"
        return s

class Child(Base):
    __tablename__ = 'Child'
    id = sqla.Column(sqla.INTEGER,autoincrement=True,primary_key=True)
    name = sqla.Column(sqla.String(length=50))
    parent_id = sqla.Column(sqla.INTEGER,sqla.ForeignKey('Parent.id'))
    #Bidirectional
    parent =relationship('Parent',back_populates = "children")
    def __str__(self):
        return "Child{id="+str(self.id)+",name="+str(self.name)+",parent_id="+str(self.parent_id)+"}"

Base.metadata.create_all(engine)
Session = sessionmaker()
Session.configure(bind=engine)
session = Session()
try:
    p = Parent(name="XYZ")
    p.children.append(Child(name="XYZChild"))
    p.children.append(Child(name="XYZChild2"))
    session.add(p)
    session.commit()
    parents = session.query(Parent)
    for parent in parents:
        print(parent)
except IntegrityError:
    Parent.__table__.drop(engine)
    session.close()