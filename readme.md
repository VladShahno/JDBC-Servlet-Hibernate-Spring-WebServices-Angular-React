In order to be able to authenticate in NIX GitLab Package Registry for PPP and get the artifacts needed for completing assignments do the following:
1. Create `${user.home}/.m2/settings.xml` file
1. Add the following to the newly created file:
```
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd"
    xmlns="http://maven.apache.org/SETTINGS/1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <servers>
        <server>
            <id>gitlab-maven</id>
            <configuration>
                <httpHeaders>
                    <property>
                        <name>Private-Token</name>
                        <value>yywYAPannroKRhDtJDfa</value>
                    </property>
                </httpHeaders>
            </configuration>
        </server>
    </servers>
</settings>
```