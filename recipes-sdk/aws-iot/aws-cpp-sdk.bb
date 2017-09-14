DESCRIPTION = "Amazon cloud services SDK for IoT based applications in C++."
LICENSE = "gpl2"

inherit dpkg debianize cmake

DEB_DEPENDS = "libssl1.0.2 libssl-dev"
DEB_DEPENDS_class-cross = "libssl1.0.2-cross libssl-dev-cross"

URL="git://github.com/aws/aws-iot-device-sdk-cpp.git"
BRANCH="master"
SRCREV = "63e8ce25e6b282719d112f3f58966cc1e78d271b"

SRC_DIR = "git"
BUILD_DIR = "build"
SRC_URI += "${URL};branch=${BRANCH};protocol=https \
            file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"

CMAKE_CXX_FLAGS="-Wno-error=deprecated-declarations"


#
# Prepare build folder, since project doesn't support in-source builds
#
do_build_prepend() {
	mkdir ${PPB}
}


debianize_build_class-cross() {
	@echo "Running build target."

	# Build static lib
	cd ${PPB} ; \
	cmake -DCMAKE_CXX_FLAGS="${CMAKE_CXX_FLAGS} -pthread" \
	-DCMAKE_TOOLCHAIN_FILE=${PP}/toolchain.cmake ${PPS} ; \
	make -j${PARALLEL_MAKE}

	# Build shared lib
	cd ${PPB} ; \
	cmake -DCMAKE_CXX_FLAGS="${CMAKE_CXX_FLAGS} -pthread" \
	      -DCMAKE_TOOLCHAIN_FILE=${PP}/toolchain.cmake \
	      -DBUILD_SHARED_LIBRARY=true ${PPS} ; \
	make -j${PARALLEL_MAKE}
}

debianize_build() {
	@echo "Running build target."

	# Build static lib
	cd ${PPB} ; \
	cmake -DCMAKE_CXX_FLAGS="${CMAKE_CXX_FLAGS}" ${PPS} ; \
	make -j${PARALLEL_MAKE}

	# Build shared lib
	cd ${PPB} ; \
	cmake -DCMAKE_CXX_FLAGS="${CMAKE_CXX_FLAGS}" \
	      -DBUILD_SHARED_LIBRARY=true ${PPS} ; \
	make -j${PARALLEL_MAKE}
}


debianize_install() {
	@echo "Running install target."
	dh_testdir
	dh_testroot
	dh_clean  -k
	install -m 0755 -d debian/${BPN}/usr/lib/${TARGET_PREFIX}
	install -m 0644 ${PPB}/lib/libaws-iot-sdk-cpp.so debian/${BPN}/usr/lib/${TARGET_PREFIX}/
	install -m 0644 ${PPB}/archive/libaws-iot-sdk-cpp.a debian/${BPN}/usr/lib/${TARGET_PREFIX}/
}


debianize_binary-arch_class-cross() {
	@echo "Running binary-arch target."
	dh_testdir
	dh_testroot
	dh_installchangelogs
	dh_installdocs
	dh_installexamples
	dh_install
	dh_installman
	dh_link
	dh_strip
	dh_compress
	dh_fixperms
	dh_installdeb
	dh_shlibdeps --dpkg-shlibdeps-params=--ignore-missing-info -l/usr/${TARGET_PREFIX}/lib
	dh_gencontrol
	dh_md5sums
	dh_builddeb
}

BBCLASSEXTEND = "cross"