# LaborMarketAuth

LaborMarketAuth, kullanıcı kimlik doğrulama ve yetkilendirme işlemlerini yönetmek için geliştirilmiş bir Spring Boot uygulamasıdır. MongoDB, Redis ve OAuth2 gibi teknolojileri kullanarak güvenli bir kimlik yönetimi sağlar.

## Başlarken

Bu bölüm, projeyi yerel ortamda çalıştırmak için gerekli adımları içermektedir.

### Gereksinimler

- Java 17 veya üzeri
- Maven 3.6.0 veya üzeri
- MongoDB
- Redis (isteğe bağlı)

### Kurulum

1. **Proje Kopyalama**:
   ```bash
   git clone https://github.com/kullaniciadi/LaborMarketAuth.git
   cd LaborMarketAuth
   ```

2. **Maven ile Bağımlılıkları Yükleme**:
   ```bash
   mvn clean install
   ```

3. **Uygulamayı Çalıştırma**:
   ```bash
   mvn spring-boot:run
   ```

### Docker Compose Desteği

Bu proje, `compose.yaml` adında bir Docker Compose dosyası içermektedir. Bu dosyada aşağıdaki hizmetler tanımlanmıştır:

- **mongodb**: [`mongo:latest`](https://hub.docker.com/_/mongo)

Docker Compose ile MongoDB'yi başlatmak için:

```bash
docker-compose -f compose.yaml up
```


### Referans Dokümantasyonu

Daha fazla bilgi için aşağıdaki bağlantılara göz atabilirsiniz:

- [Apache Maven Resmi Dokümantasyonu](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Referans Kılavuzu](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
- [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.nosql.mongodb)
- [Spring Security](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web.security)
- [Spring Boot Email](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#boot-features-email)
- [Spring Boot JWT](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#boot-features-security-jwt)

### Kılavuzlar

Aşağıdaki kılavuzlar, bazı özelliklerin nasıl kullanılacağını göstermektedir:

- [MongoDB ile Veri Erişimi](https://spring.io/guides/gs/accessing-data-mongodb/)
- [Web Uygulamasını Güvenli Hale Getirme](https://spring.io/guides/gs/securing-web/)
- [Spring Boot ve OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [RESTful Web Servisi Oluşturma](https://spring.io/guides/gs/rest-service/)

### Maven Parent Overrides

Maven'in tasarımı gereği, öğeler ana POM'dan proje POM'una miras alınır. Mirasın çoğu iyi olsa da, `<license>` ve `<developers>` gibi istenmeyen öğeleri de miras alır. Bunu önlemek için, proje POM'u bu öğeler için boş geçersiz kılmalar içerir. Eğer farklı bir ana POM'a geçiş yaparsanız ve mirası gerçekten istiyorsanız, bu geçersiz kılmaları kaldırmanız gerekir.

## Katkıda Bulunma

Katkıda bulunmak isterseniz, lütfen bir pull request oluşturun veya sorunları bildirin.

## Lisans

Bu proje [MIT Lisansı](LICENSE) altında lisanslanmıştır.