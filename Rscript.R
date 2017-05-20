#filePath="C:\\Users\\Admin\\Desktop\\Java_ML\\src\\samplelas.las"
conn=file(xyz,open="r")
Text=readLines(conn)
for (i in 1:length(Text)){
  if (substr(Text[i],1,5)=="~Asc "){
    value=0
    break}
  if (substr(Text[i],1,3)=="~A "){
    value=1
    break}
}
close(conn)
tabl=read.table(header = F, text=Text[(i+1):length(Text)])

mycols=c()
units=c()
parnames=c()
for (x in 1:dim(tabl)[2]){
  vec=unlist(strsplit(Text[i-x-value]," "))
  vec=vec[!vec==""]

  mycols=append(vec[1],mycols)
  temp=unlist(strsplit(vec[1],"\\."))
  parnames=append(temp[1],parnames)
  units=append(temp[2],units)
}
names(tabl)=parnames
df=as.data.frame(tabl)
df[df==-999.25]=NA
df
