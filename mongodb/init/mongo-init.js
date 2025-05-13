db.createUser({
    user: process.env.MONGO_INITDB_USERNAME,
    pwd:  process.env.MONGO_INITDB_PASSWORD,
    roles: [{
        role: 'readWrite',
        db: process.env.MONGO_INITDB_DATABASE
    }]
});

db = new Mongo().getDB(process.env.MONGO_INITDB_DATABASE);

db.createCollection('Assignment', { capped : false})
db.createCollection('Problem', { capped : false})
db.createCollection('Submission', { capped : false})
db.createCollection('Student', { capped : false})

