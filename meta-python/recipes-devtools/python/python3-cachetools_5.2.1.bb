SUMMARY = "Extensible memoizing collections and decorators"
HOMEPAGE = "https://github.com/tkem/cachetools"
DESCRIPTION = "This module provides various memoizing \
collections and decorators, including variants of the \
Python 3 Standard Library @lru_cache function decorator."
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ec55353c80c662e4255f8889a0ca558"

inherit pypi python_setuptools_build_meta ptest

SRC_URI += " \
	file://run-ptest \
"

RDEPENDS:${PN} += " \
	${PYTHON_PN}-math \
"

RDEPENDS:${PN}-ptest += " \
	${PYTHON_PN}-pytest \
"

do_install_ptest() {
	install -d ${D}${PTEST_PATH}/tests
	cp -rf ${S}/tests/* ${D}${PTEST_PATH}/tests/
}

SRC_URI[sha256sum] = "5991bc0e08a1319bb618d3195ca5b6bc76646a49c21d55962977197b301cc1fe"

BBCLASSEXTEND = "native nativesdk"
