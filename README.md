## Grouper
grouper is an application that read a comma delimited file (like csv) 
and group lines based on combinnation of lastname and firstname

## Design Consideration
I had two approaches:
1. The first is by: read -> sign -> sort -> squash
2. The second is by: load -> group by hash

So the diagram is:

 Bootstrap -> Grouper -> CSVFileReader 

## Implementation
   code in Java 8 and build by maven

## build step: (on MacOS)

```
On terminal:

$git clone git@github.com:yaohuang2005/grouper.git
$cd grouper
$mvn clean package

This command will generate grouper-1.0-SNAPSHOT.jar under the target directory

## test
$cd target
$cp ../src/main/resources/bin/grouper.sh .
$cp ../src/test/resources/input_file .

$./grouper.sh  input_file
-------use approach 1, by sign, sort, squash ----------
0:
PID6,CLAR^OSWALD,F,19890224
1:
PID4,CLARA^OSWALD,F,19890224
PID8,CLARA^oswald,F,19890224
PID14,CLARA^OSWALD^COLEMAN,F,19890224
2:
PID12,NOBLE^DONN,F,19780405
3:
PID10,NOBLE^DONNA,F,19780405
4:
PID7,POND^AMELIA,F,20010911
5:
PID1,POND^AMY,F,19890224
PID3,POND^AMY,F,19890224
PID5,POND^AMY,F,20010911
6:
PID9,TYLER^ROSE,F,20000101
PID11,TYLER^ROSE,F,20000101
PID13,TYLER^ROSE,F,20000102
7:
PID2,WILLIAMS^RORY,M,19881102


-------use approach 2, by hash ----------
0:
PID10,NOBLE^DONNA,F,19780405
1:
PID7,POND^AMELIA,F,20010911
2:
PID12,NOBLE^DONN,F,19780405
3:
PID6,CLAR^OSWALD,F,19890224
4:
PID9,TYLER^ROSE,F,20000101
PID11,TYLER^ROSE,F,20000101
PID13,TYLER^ROSE,F,20000102
5:
PID1,POND^AMY,F,19890224
PID3,POND^AMY,F,19890224
PID5,POND^AMY,F,20010911
6:
PID2,WILLIAMS^RORY,M,19881102
7:
PID4,CLARA^OSWALD,F,19890224
PID8,CLARA^oswald,F,19890224
PID14,CLARA^OSWALD^COLEMAN,F,19890224

Process finished with exit code 0
