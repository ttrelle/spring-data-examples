start mongod --rest --port 8000 --dbpath /var/master --replSet cluster0
start mongod --rest --port 8001 --dbpath /var/slave1 --replSet cluster0
start mongod --rest --port 8002 --dbpath /var/slave2 --replSet cluster0