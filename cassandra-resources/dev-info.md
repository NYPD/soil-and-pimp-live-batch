## INFO ON DEV SEVRER:

- authenticator: PasswordAuthenticator 
- authorizer: CassandraAuthorizer 
- role_manager: CassandraRoleManager

localhost/127.0.0.1 should work

```
 name             | super
------------------+-------
        cassandra |  True
             nypd |  True
 soilandpimpbatch | False
 
  role             | username         | resource               | permission
------------------+------------------+------------------------+------------
 soilandpimpbatch | soilandpimpbatch | <keyspace soilandpimp> |     SELECT
 soilandpimpbatch | soilandpimpbatch | <keyspace soilandpimp> |     MODIFY
 ```