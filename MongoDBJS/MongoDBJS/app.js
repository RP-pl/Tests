'use strict';
var mongodb = require('mongodb')
var MongoClient = mongodb.MongoClient;
MongoClient.useUnifiedTopology = true
MongoClient.connect(
    'mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=false',
    (error, db) => {
        if (error) throw error
        console.log('connected')
        var mydb = db.db('userdb')
        var coll = mydb.collection('users')
        coll.deleteOne({ "_id": 5 })
        coll.find().sort({ "name": -1 }).toArray((error, res) => {
            if (error) throw error;
            console.log(res)
        })
        coll.insertOne({ "_id": 5, "name": "JSUser", "age": 23 })
        coll.find().sort({"name":1}).toArray((error, res) => {
            if (error) throw error;
            console.log(res)
        })
        db.close()
    }
)
setTimeout(() => { },5000)
