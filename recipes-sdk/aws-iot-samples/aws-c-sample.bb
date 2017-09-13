DESCRIPTION = "Sample for IoT based applications in embedded C, making use of aws-iot-SDK."
LICENSE = "gpl2"

inherit dpkg debianize
DEPENDS += "aws-c-sdk"

URL = "https://github.com/aws/aws-iot-device-sdk-embedded-C/tree/master/samples/linux/subscribe_publish_cpp_sample"

SRC_DIR = "src"
SRC_URI += " file://src \
             file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"

debianize_build[target] = "build"
debianize_build() {
	@echo "Running build target."
	make -j${PARALLEL_MAKE} TARGET_PREFIX=${TARGET_PREFIX}
}


debianize_clean[target] = "clean"
debianize_clean() {
	@echo "Running clean target."
}

debianize_install[target] = "install"
debianize_install[tdeps] = "build"
debianize_install() {
	@echo "Running install target."
	dh_testdir
	dh_testroot
	dh_clean  -k
	install -m 0755 -d debian/${BPN}/usr/bin
	install -m 0755 ${PPS}/subscribe_publish_cpp_sample debian/${BPN}/usr/bin
}
