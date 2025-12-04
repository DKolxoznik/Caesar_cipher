FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

# Компилируем все файлы с preview
RUN find src -name "*.java" > sources.txt && \
    javac --enable-preview --release 25 -d classes @sources.txt

# Запускаем с preview
CMD ["java", "--enable-preview", "-cp", "classes", "java_home.Main"]