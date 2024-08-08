# 使用 JDK 22 基础镜像
FROM openjdk:22-jdk

# 维护者信息
LABEL maintainer="2552831709@qq.com"

# 创建应用目录
RUN mkdir -p /app

# 设置工作目录
WORKDIR /app

# 复制项目的 pom.xml 和其他依赖文件到工作目录
COPY pom.xml /app

# 下载项目的依赖包（利用 Docker 的缓存功能，加速后续构建）
RUN ./mvnw dependency:go-offline -B

# 复制项目的源代码到工作目录
COPY ./src /app/src

# 构建 Spring Boot 应用
RUN ./mvnw package -DskipTests

# 暴露端口
EXPOSE 8080

# 运行 Spring Boot 应用
CMD ["java", "-jar", "target/api_backstage-0.0.2-SNAPSHOT.jar"]