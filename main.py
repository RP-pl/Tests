import sqlalchemy as sqla
from sqlalchemy import create_engine
from sqlalchemy.exc import InvalidRequestError, IntegrityError
from sqlalchemy.orm.session import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
password=''
engine = create_engine("mysql+mysqlconnector://root:"+password+"localhost:3306/userdb")

Base = declarative_base()
class User(Base):
    __tablename__ = 'UsersSQLA'
    id = sqla.Column(sqla.INTEGER,primary_key=True)
    name = sqla.Column(sqla.String(length=50))
    age = sqla.Column(sqla.SMALLINT)
    def __str__(self):
        return "User{id : " + str(self.id)+ " name : " + self.name + " age : " + str(self.age) + "}"
Base.metadata.create_all(engine)

Session = sessionmaker()
Session.configure(bind=engine)
session = Session()

try:
    id=1
    for name in ["A","B","C","D","E","F","G","H","I"]:
        user = User(id=id,name=name,age=id)
        session.add(user)
        id+=1
    session.commit()
    users = session.query(User)
    for user in users:
        print(user)
    session.close()
except IntegrityError:
    User.__table__.drop(engine)
    session.close()