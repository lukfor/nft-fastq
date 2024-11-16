package com.github.lukfor.nft.fastq

import java.nio.file.Path

class PathExtension {

    static FastqFile getFastq(Path self) throws IOException {
        return new FastqFile(self)
    }
}