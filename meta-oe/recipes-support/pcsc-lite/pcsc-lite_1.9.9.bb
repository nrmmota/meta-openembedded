SUMMARY = "PC/SC Lite smart card framework and applications"
HOMEPAGE = "http://pcsclite.alioth.debian.org/"
LICENSE = "BSD-3-Clause & GPL-3.0-or-later"
LICENSE:${PN} = "BSD-3-Clause"
LICENSE:${PN}-lib = "BSD-3-Clause"
LICENSE:${PN}-doc = "BSD-3-Clause"
LICENSE:${PN}-dev = "BSD-3-Clause"
LICENSE:${PN}-dbg = "BSD-3-Clause & GPL-3.0-or-later"
LICENSE:${PN}-spy = "GPL-3.0-or-later"
LICENSE:${PN}-spy-dev = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=628c01ba985ecfa21677f5ee2d5202f6"
DEPENDS = "autoconf-archive-native"

SRC_URI = "https://pcsclite.apdu.fr/files/${BP}.tar.bz2"
SRC_URI[sha256sum] = "cbcc3b34c61f53291cecc0d831423c94d437b188eb2b97b7febc08de1c914e8a"

inherit autotools systemd pkgconfig perlnative

EXTRA_OECONF = " \
    --disable-libusb \
    --enable-usbdropdir=${libdir}/pcsc/drivers \
"

S = "${WORKDIR}/pcsc-lite-${PV}"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} udev"
PACKAGECONFIG:class-native ??= ""

PACKAGECONFIG[systemd]  = ",--disable-libsystemd,systemd,"
PACKAGECONFIG[udev] = "--enable-libudev,--disable-libudev,udev"

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-lib ${PN}-doc ${PN}-spy ${PN}-spy-dev"

RRECOMMENDS:${PN} = "ccid"
RRECOMMENDS:${PN}:class-native = ""
RPROVIDES:${PN}:class-native += "pcsc-lite-lib-native"

FILES:${PN} = "${sbindir}/pcscd"
FILES:${PN}-lib = "${libdir}/libpcsclite*${SOLIBS}"
FILES:${PN}-dev = "${includedir} \
                   ${libdir}/pkgconfig \
                   ${libdir}/libpcsclite.la \
                   ${libdir}/libpcsclite.so"

FILES:${PN}-spy = "${bindir}/pcsc-spy \
                   ${libdir}/libpcscspy*${SOLIBS}"
FILES:${PN}-spy-dev = "${libdir}/libpcscspy.la \
                       ${libdir}/libpcscspy.so "

RPROVIDES:${PN} += "${PN}-systemd"
RREPLACES:${PN} += "${PN}-systemd"
RCONFLICTS:${PN} += "${PN}-systemd"
SYSTEMD_SERVICE:${PN} = "pcscd.socket"
RDEPENDS:${PN}-spy += "python3-core"

BBCLASSEXTEND = "native"
