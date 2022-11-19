# 低代码平台后端
## 部署
### 测试环境
``` shell
./mvnw clean
./mvnw package -DskipTests=true
nohup java -jar visual-project-server-1.0-SNAPSHOT.jar --spring.profiles.active=test  >./log.txt 2>&1 &
```