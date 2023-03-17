# kafka-learn
## Basic Terminal Commands

### Check kafka is working properly

`kafka-topics.sh` 

### Create topic 

`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic first_topic`

### Create topic with paritions

`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic second_topic --partitions 3`

### Create topic with replication

`kafka-topics.sh --bootstrap-server localhost:9092 --create --topic third_topic --partitions 3 --replication-factor 2`

`kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 1`

### List topics

`kafka-topics.sh --bootstrap-server localhost:9092 --list` 

### Describe a topic

`kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe`

### Delete a topic 

`kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --delete`
(only works if delete.topic.enable=true)

### Producer

`kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic`\
`> Hello World`\
`> My name is Kishor`\
`> Kafka is intersting`\
`> ^C  (<- Ctrl + C is used to exit the producer)`


### Producing with properties
`kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --producer-property acks=all`\
`> demo msg is ack`\
`> random text for test`\
`> wow its intersting!`

### Produce with keys
`kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:`\
`> id:123`\
`> name:kb`

### Consuming
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic`

### consuming from beginning
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --from-beginning`

### Display key, values and timestamp in consumer
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --from-beginning`\
`CreateTime:1678876676759        Partition:0     null    Hello world`\
`CreateTime:1678876681930        Partition:0     null    My name is Kishor`


### Start one consumer
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-first-application`

### Start one producer and start producing
`kafka-console-producer.sh --bootstrap-server localhost:9092 --producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner --topic third_topic`

### Start another consumer part of the same group. See messages being spread
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-first-application`

### Start another consumer part of a different group from beginning
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-second-application --from-beginning`

### List consumer groups
`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list`
 
### describe one specific group
`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-second-application`

### Dry Run: reset the offsets to the beginning of each partition
`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --topic third_topic --dry-run`
