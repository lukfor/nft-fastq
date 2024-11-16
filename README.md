# nft-fastq

nf-test plugin to provide support for FASTQ files.

## Requirements

- nf-test version 0.7.0 or higher

## Setup

To use this plugin you need to activate the `nft-fastq` plugin in your `nf-test.config` file:

```
config {
  plugins {
    load "nft-fastq@0.0.1"
  }
}
```

**Warning: Plugin is not yet deployed to the plugin repository.**

## Build and Test

The plugin is written in Groovy and uses Gradel to build and test it.

```bash
./gradlew test-plugin 
```

## Examples

nft-fastq extends `path` by a `fastq` property that can be used to read FASTQ files:

```groovy
def fastqFile = path(process.out.files.get(0)).fastq

// Assertion 1: Check if the file is a valid FASTQ file
assert fastqFile.isFastqFile() == true

// Assertion 2: Check if the number of records is correct (3 records in the provided test file)
assert fastqFile.getNumberOfRecords() == 3

// Assertion 3: Get all sequences and check their values
def sequences = fastqFile.getAllSequences()
assert sequences.size() == 3
assert sequences == ['AGCTTAGCTA', 'CGTACGATCG', 'TGCATGCATG']

// Assertion 4: Get all read names and check their values
def readNames = fastqFile.getAllReadNames()
assert readNames.size() == 3
assert readNames == ['SEQ_ID_1', 'SEQ_ID_2', 'SEQ_ID_3']

// Assertion 5: Get all quality scores and check their values
def qualityScores = fastqFile.getAllQualityScores()
assert qualityScores.size() == 3
assert qualityScores == ['IIIIIIIIII', 'JJJJJJJJJJ', 'KKKKKKKKKK']

// Assertion 6: Get a specific record by index (index 1 should give the second record)
def record = fastqFile.getRecordByIndex(1)
assert record != null
assert record.getReadHeader() == 'SEQ_ID_2'
assert record.getReadString() == 'CGTACGATCG'
assert record.getBaseQualityString() == 'JJJJJJJJJJ'

// Assertion 6: Get a specific record by name
def record2 = fastqFile.getRecordByName('SEQ_ID_3')
assert record2 != null
assert record2.getReadHeader() == 'SEQ_ID_3'
assert record2.getReadString() == 'TGCATGCATG'
assert record2.getBaseQualityString() == 'KKKKKKKKKK'
```

## Contact

- Lukas Forer @lukfor