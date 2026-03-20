# ============================================================
# Stage 1: BUILD
# Use a full JDK image to compile and package the application
# ============================================================
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml first (leverages Docker layer caching)
# This means dependencies are re-downloaded ONLY if pom.xml changes
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies in a separate layer (cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Now copy source code (this layer changes frequently, so it comes last)
COPY src ./src

# Build the fat JAR, skip tests (tests run in CI pipeline, not here)
RUN ./mvnw package -DskipTests -B

# ============================================================
# Stage 2: RUNTIME
# Use a slim JRE image — we don't need the full JDK to RUN the app
# This keeps the final image small (~200MB vs ~500MB+)
# ============================================================
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Create a non-root user for security best practices
# Never run your app as root inside a container!
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copy ONLY the built JAR from the builder stage (not the entire build context)
COPY --from=builder /app/target/*.jar app.jar

# Change ownership to the non-root user
RUN chown appuser:appgroup app.jar

USER appuser

# Document which port this app uses (doesn't actually expose it — docker-compose/run does)
EXPOSE 8080

# Use exec form (not shell form) so signals like SIGTERM are handled correctly
# This allows graceful shutdown
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", \
    "app.jar"]
