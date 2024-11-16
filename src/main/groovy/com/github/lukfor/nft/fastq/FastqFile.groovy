package com.github.lukfor.nft.fastq

import htsjdk.samtools.fastq.FastqReader
import htsjdk.samtools.fastq.FastqRecord
import java.nio.file.Path
import java.nio.file.Files
import java.io.File

class FastqFile {

    private final Path fastqPath
    private final File fastqFile

    // Constructor that accepts a Path and initializes the File object
    FastqFile(Path path) {
        if (path == null || !Files.exists(path) || !Files.isReadable(path)) {
            throw new IllegalArgumentException("Invalid path: ${path}")
        }
        this.fastqPath = path
        this.fastqFile = path.toFile()
    }

    // Method to get all reads from the FASTQ file
    List<FastqRecord> getAllReads() {
        List<FastqRecord> records = []
        FastqReader reader = new FastqReader(fastqFile)
        reader.each { record ->
            records << record
        }
        reader.close()
        return records
    }

    // Method to get a specific read by index (zero-based)
    FastqRecord getRecordByIndex(int index) {
        FastqReader reader = new FastqReader(fastqFile)
        int currentIndex = 0
        FastqRecord record = null
        for (FastqRecord r in reader) {
            if (currentIndex == index) {
                record = new FastqRecord(r)
                break
            }
            currentIndex++
        }
        reader.close()
        return record
    }


    // Method to get a specific read by name
    FastqRecord getRecordByName(String name) {
        FastqReader reader = new FastqReader(fastqFile)
        FastqRecord record = null
        for (FastqRecord r in reader) {
            if (r.getReadHeader() == name) {
                record = new FastqRecord(r)
                break 
            }
        }        
        reader.close()
        return record
    }

    // Method to get all sequences in the FASTQ file
    List<String> getAllSequences() {
        List<String> sequences = []
        FastqReader reader = new FastqReader(fastqFile)
        reader.each { record ->
            sequences << record.getReadString()
        }
        reader.close()
        return sequences
    }

    // Method to get all read names in the FASTQ file
    List<String> getAllReadNames() {
        List<String> names = []
        FastqReader reader = new FastqReader(fastqFile)
        reader.each { record ->
            names << record.getReadHeader()
        }
        reader.close()
        return names
    }

    // Method to get all quality scores in the FASTQ file
    List<String> getAllQualityScores() {
        List<String> qualityScores = []
        FastqReader reader = new FastqReader(fastqFile)
        reader.each { record ->
            qualityScores << record.getBaseQualityString()
        }
        reader.close()
        return qualityScores
    }

    // Method to get the total number of records in the FASTQ file
    int getNumberOfRecords() {
        int count = 0
        FastqReader reader = new FastqReader(fastqFile)
        reader.each { _ ->
            count++
        }
        reader.close()
        return count
    }

    // Helper method to check if the file is a valid FASTQ file based on its extension
    boolean isFastqFile() {
        return fastqPath.toString().toLowerCase().endsWith('.fastq') || fastqPath.toString().toLowerCase().endsWith('.fq')
    }

    // Getter for the Path object
    Path getPath() {
        return fastqPath
    }
}
