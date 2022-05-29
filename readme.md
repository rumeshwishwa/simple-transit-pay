# Simple Transit Pay

Simple transit pay is a application that calculate the user transit jouney cost based on the get in and get off locations of the user. User transit details can be provided as a simple csv file and calculated output details will be write again to a csv file.
To run the application you will need, 
1. java 8+
2. maven
3. git

execution steps

1. clone the project
   
``` 
 git clone https://github.com/rumeshwishwa/simple-transit-pay.git
```

2. change directory in to simple-transit-pay

``` 
 cd simple-transit-pay
```

3. execute mvn clean install

``` 
 mvn clean install
```

4. run the application

``` 
 ./run.sh <input-csv-file-path> <output-csv-file-path>
```
