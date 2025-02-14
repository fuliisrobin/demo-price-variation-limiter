tar -czf context.tar.gz Dockerfile -C target demo-trading-system.jar
docker build -t "${IMAGE_REGISTRY}/${IMAGE_NAME}:v${BUILD_NUMBER}" - < context.tar.gz