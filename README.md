# Spring ehcache - attachment files expiring cache

## build
```
mvn clean package
```

## run
```
mvn spring-boot:run
```
ou exécuter la main method de RestCacheUploadApplication.java

[http://localhost:8080](http://localhost:8080)

## v1 : Filesystem
[http://localhost:8080/fs](http://localhost:8080/fs)

Voir :
```
FileSystemFileUploadController
```

## v2 : In memory
[http://localhost:8080/mem](http://localhost:8080/mem)

Voir :
```
InMemoryFileUploadController
```

## v3 : In memory with expiring cache
[http://localhost:8080/cachemem](http://localhost:8080/cachemem)

Le TTL du cache doit être égal à la durée de session HTTP. Ici c'est moins, pour le test :
```
<ttl unit="minutes">2</ttl>
```

Voir :
```
CachedInMemoryFileUploadController
```

## Documentation :

https://spring.io/guides/gs/rest-service/

https://www.baeldung.com/spring-boot-ehcache

https://spring.io/guides/gs/uploading-files/
