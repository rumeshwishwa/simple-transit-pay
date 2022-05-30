cd target
echo 'Output will be written in to %1 file'
java -jar simple-transit-pay-1.0.0.jar --file.write.name=%1
cd ..