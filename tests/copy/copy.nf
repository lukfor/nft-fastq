process COPY {

  input:
    path fasta

  output:
     path "output.fastq", emit: files


  """
	cp ${fasta} output.fastq
  """

}
