
conn=file(xyz,open="r")      # xyz -> stores path address of the las file, taken as input from the user
Text=readLines(conn)         # Reading all the lines of file as text

# Finding the beginning of data
for (i in 1:length(Text)){
  if (substr(Text[i],1,5)=="~Asc "){
    y=0
    break}
  if (substr(Text[i],1,3)=="~A "){
    y=1
    break}
}
close(conn)
tabl=read.table(header = F, text=Text[(i+1):length(Text)])   # Reading in all the numeric data as a structured table

mycols=c()     # Column names of stored table
parnames=c()   # Parameter names
units=c()      # Units of the parameters

for (x in 1:dim(tabl)[2]){                     # Looping through no. of parameters
  vec=unlist(strsplit(Text[i-x-y]," "))
  vec=vec[!vec==""]
  temp=unlist(strsplit(vec[1],"\\."))
  
  mycols=append(vec[1],mycols)
  parnames=append(temp[1],parnames)
  units=append(temp[2],units)
}

names(tabl)=parnames                           # Setting the column names to parameter names 
df=as.data.frame(tabl)
df[df==-999.25]=NA                             # Setting all occurences of -999.25 to NAs
df

# write.csv("Data.csv",row.names=F,df)
