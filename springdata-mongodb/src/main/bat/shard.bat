start mongod --shardsvr --rest --port 9000 --dbpath /var/shard1
start mongod --shardsvr --rest --port 9001 --dbpath /var/shard2
start mongod --configsvr --rest --port 9002 --dbpath /var/conf1
pause
start mongos --port 9003 --configdb tmp-pc:9002 --chunkSize 1

