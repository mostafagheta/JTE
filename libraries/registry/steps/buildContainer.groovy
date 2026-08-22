void call() {
    echo "Building Container Image..."
    def image = resolveAppImage()
    env.APP_IMAGE = image
    echo "Building ${image}..."
    sh "docker build -t ${image} ."
}
