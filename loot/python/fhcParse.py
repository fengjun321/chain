from csv import reader
import ctypes

from fastApi.fhcItem import HFCData

with open("herocard_data.csv", 'a+') as f:
# open file in read mode
    with open('fhc.csv', 'r') as read_obj:
        # pass the file object to reader() to get the reader object
        csv_reader = reader(read_obj)
        # Iterate over each row in the csv using reader object
        f.write("tokenId,level,rank,material,identity,occu,camp,cardName,end,str,agi,cri,psi,spi\n")
        p = HFCData()
        for row in csv_reader:
            # row variable is a list that represents a row in csv
            p.parseData(int(row[1]))
            tokenId = str(row[0])
            #print(tokenId+ ","+ p.getFhcRankValue() + "," + p.getFhcMaterialValue()+ "\n")
            f.write(tokenId+","+str(p.getLevel())+ ","+ p.getFhcRankValue() + "," + p.getFhcMaterialValue()+  "," +p.getIdentity()+ \
                ","+p.getProfession()+","+p.getFhcCampValue()+","+p.getFhcNameValue()+","+str(p.getEndurance())+","+str(p.getStrength())+ \
                ","+str(p.getAgility())+","+str(p.getCrit())+","+str(p.getPisonic())+","+str(p.getSpirit())+"\n")



if __name__ == '__main__':
    p = HFCData()
    p.parseData()
