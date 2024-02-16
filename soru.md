## 1- 7071'de ayaga kalkacak bir "user-service" mikroservisi olusturalim. 
## Ve iki servisimiz icin de postgres baglantilarini gerceklestirelim. (java13AuthDB, java13UserDB)
## ODEV SORUSU : AUTH'da gerekli katmanlari ekleyelim. Sonrasinda bir "register()" metodu yazalim. register metodu
## DTO alsin ve geriye DTO donsun. Bunun icin gerekli mapper islemlerini de gerceklestirelim.

## 2- Disaridan login olmak icin gerekli parametreleri alalim. eger bilgiler dogru ise bize true, yanlis ise false donsun.

## 3- Validasyon islemlerini yapalim. Aklimiza gelen basit validasyonlari kullanalim.

## 4- Kullanıcının Status'unu aktif hale getirmek için aktivasyon kod doğrulaması yapan bir metot yazınız.

## 5- Auth'da status'u ACTIVE hale getirdigimizde user-service'deki status da aktif hale gelsin.

## 6- Login metodunu revize edelim. Bize bir token üretip o tokeni geri dönsün. Sadece aktif kullanıcılar login olabilsin.

## 7- User'da email'imi degistirdigimde auth'da da degissin istiyorum. Bunun icin user-service'den -> auth-service'e bir feign client baglantisi gerceklestirelim.