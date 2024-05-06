# This file is used to describe the image of the application so we can reliably recreate it anywhere we want
# First thing we need to do is to provide a BASE IMAGE to start FROM
FROM amazoncorretto:17

# We'll need to COPY our packaged JAR file from here to our containers storage
COPY target/app.jar app.jar

# Next thing we need to consider is how to EXPOSE
EXPOSE 8080

# The final thing we need to do is provide the CMD to start the application itself
CMD ["java", "-jar", "app.jar"]