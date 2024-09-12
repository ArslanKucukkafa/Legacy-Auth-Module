# Fikirler

* @Permissionable ve @ManageAccess annotationları oluşturmamın sebebi endpointlere erişim yetkisini actionlar ve classlar üzerinden kolay bir şekilde sınırlandırmaya çalışmak.
* Classlar üzerinden sınırlandırmanın, endpoint url'lerinin sınırlandırılmasına göre daha esnek bir yapı oluşturacağını düşünüyorum.

## Neden Classlar Üzerinden Sınırlandırma?
* Zaten controller classlrının yanlızca o service için oluşturulduğunu düşünürsek, burda toplu bir rol yönetimi yapılaması ve admin - user arasında farklı yetkilendirmeler yapılabilmesi için classlar üzerinden sınırlandırma yapılması daha mantıklı olacaktır.
* Farklı birşeler denemek istedim
* SecurityConfig de url stringleri için tek tek sınırlandırma yapmak yerine, yada parent string ile sınırlandırma yapmak olayı karmaşıklaştırdıgında dolayı, ayrıca kullanıcıya özel bir action `READ,DELETE,UPDATE,CREATE` gibi yetkileri classlar üzerinden sınırlandırmak daha mantıklı olacaktır.
* Ayrıca Intercopter ,Filter gibi yapıların kullanımıyla daha fazla deneyim kazanmak istedim.

## Nasıl Çalışır ?
@Permissionable annotationu ile işaretlenen class, Interceptor'den kullanıcı yetlki kontrol edilerek geçer.
@ManageAccess annotationu ile işaretlenen methodlar ise Interceptorde kullanıcının o method için belirtilen action tanımına uygun olması beklenir.
@Permissionble olmayan bir controllerın @ManageAcces methodu olması beklenmez, çünkü bir işe yaramaz.

## Ne kazandırması bekleniyor ?

1. Toplu yetki kontrol sınırlamalarının yapılabilmesi. Örnegin NotificationController oldugunu farz edelim. Burdaki tüm Get request beklenen methodları check paket halinde kontrol edebiliriz
2. Methoda özel bir kısıtmlama yapabiliriz. Örnegin NotificationController içindeki getNotification methodu için sadece admin yetkisi olması gerektiğini belirtebiliriz.

## Fıkır-2
* Adresses, Contact, Accounts, Billng gibi kısımların zorunlu olup olmaması opsiyonel olabilir.