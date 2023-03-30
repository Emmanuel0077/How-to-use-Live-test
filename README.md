# How-to-use-Live-test


Live test is a java file (LiveTests.java) used to test code units, as well as see the results of the codes in question.
Unit testing refers to the testing of individual components in the source code, such as classes and their provided methods. The writing of tests reveals whether each class and method observes or deviates from the guideline of each method and class having a single, clear responsibility.
To use the Live Tests file, follow the instructions below:
1 – Download the LiveTests.java file from the GitHub repository (repository link), or ask your supervisor for it.
2 - Open the file and select the port of the Database you want to test your method on.
Note: The selection of the DB is defined on “private final String dbconnectionUrl = __________”
Ex1: private final String dbConnectionUrl = "jdbc:mysql://localhost:3320/openmrs?useSSL=false";
Where: 
localhost is where your server is located.
3320 is the port of the DB.
EX2: private final String dbConnectionUrl = "jdbc:mysql://thebugatti.e-saude.net/fgh-v3-c4
:3337/openmrs?useSSL=false";
Where:
thebugatti.e-saude.net/fgh-v3-c4 is where your server is located.
3337 is the port to access the Fgh Database. 
3 – Identify the method that tests the specific kind of code unit you want to test (CohortDefinition, DataSetDefinition, calculation).
4 – Change the class name and method to be tested in “cohortDefinition cd = _______________”, to test the specific method you want to test. Ex:  CohortDefinition cd = txPvlsCohortQueries.getPregnantPatients().
Where: 
txPvlsCohortQueries: is the name of the class where your method was created.
Note: If the class you’re working with is not included in the Live Tests file, you will have to make an dependency injection. Ex: @Autowired private TxPvlsCohortQueries txPvlsCohortQueries;
Where  TxPvlsCohortQueries is the name of the class, and  txPvlsCohortQueries is the name of the variable.
getPregnantPatients(): is the method to be tested.
5 - Identify the parameters that are considered by the method you want to test, checking the mappings of the method, in the class where the method was created. 
Ex: startDate, endDate, location.
6 -  Define the parameters that are considered in your method, by control clicking (Ctrl + left click) on the parameter that is being mapped in the Livetests file.
	Ex: Define startdate as “2022-06-21”, endDate as “2022-09-20” and location as “399”
Then you click on the run button to run your test.
Enjoy.

