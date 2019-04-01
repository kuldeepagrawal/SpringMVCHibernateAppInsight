# SpringMVCHibernateAppInsight

1. If you will try to run the project in Tomcat, it may report the following error message. 
   java.lang.SecurityException: class "com.microsoft.applicationinsights.web.spring.internal.InterceptorRegistry$$EnhancerBySpringCGLIB$$bdf4b6ec"'s signer information does not match signer information of other classes in the same package
   ERROR: org.springframework.web.servlet.DispatcherServlet - Context initialization failed
   java.lang.IllegalStateException: Cannot load configuration class: com.microsoft.applicationinsights.web.spring.internal.InterceptorRegistry

   There Could you please replace servlet-context.xml with attached one and try.
   
 2. You may get error that the database refused my connection. Try to setup own MySQL database and run the test. 
    Error : The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.
    org.hibernate.exception.GenericJDBCException: Could not open connection] with root cause
    java.net.ConnectException: Connection refused: connect
    
    You need to install MySQL and configure it. While installing MySQL keep username and password as “root”. Connect to mysql local host.
    Then create database testdb and create table person in it

	 create database testdb;
    use testdb;
    CREATE TABLE `Person` ( `id` int(11) unsigned NOT NULL AUTO_INCREMENT,   `name` varchar(20) NOT NULL DEFAULT '',   `country` varchar(20) DEFAULT NULL,   PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

