import json
import ctypes


image_prefix = 'https://intro.firework.games/fhc/'
nft_edition=[
    'Standard',
    'Halloween',
]
nft_camp=[
    'AsmanZerg',
    'EmpireofAmda',
    'ConfederationofEarth',
    'EternalProtoss',
]
nft_rank=[
    'Common',
    'Rare',
    'Epic',
    'Legend',
    'LimitedLegend',
]
nft_material=[
    'DarkIron',
    'Silver',
    'Gold',
]
nft_name_standard = [
    [
        'Adeyemi','Samuel','Perez','Nketiah','Zaka','Lautaro','Ulmer','Komota','Menama','PatsonDaka','Brett','Barnes','Alberto','Common14','Common15','Common16','Common17',
    ],
    [
        'Kane','Alaba','Marko','Yuca','KyleWalker','Yurion','ToanNgo','Frendado','Karim','Brett','Kimmich','Berg','Carvajal','Common14','Common15','Common16','Common17',
    ],
    [
        'Moroni','Balde','Ancelotti','Soualiho','Adama','Guido','Lopes','Common8','Hoffmann','Common10','Veretout','Vardy','Aaron','Common14','Lacazette','Immobile','Werner',
    ],
    [
        'Estel','Naicelea','Nahta','Alasse','Almárea','Jean','Yesta','Brobbey','Brian','Alario','Pelecco','Ebenezer','Kieran','Common14','Common15','Common16','Common17',
    ]
]
nft_name_halloween=[
    [
        'Adeyemi','Perez','Samuel','Brett','Patson_Daka','Ulmer','Komota','Barnes','Common1','Common2','Common3','Common4','Common5','Common6','Common7',
    ],
    [
        'Kane','Marko_Tovic','Alaba','Frendado','Brett','Yurion','Toan_Ngo','Common1',
    ],
    [
        'Moroni','Balde','Ancelotti','Soualiho','Adama','Guido','Lopes','Falcao','Hoffmann','Common1','Common2','Common3','Common4','Common5','Common6','Common7',
    ],
    [
        'Estel','Naicelea','Naica','Yesta','Nahta','Pelecco','Alasse','Almárea','Common1',
    ],
] 
nft_identity = ['Leader','Scientist','Commander','Star Soldier','Common Soldier']
nft_occupation = ['Omni Warrior','Psionicist','Bounty Hunter','Wanderer','Liquidator']
nft_power_rank_material_Halloween = [
    [10,35,75],
    [15,40,80],
    [139,164,204],
    [95,120,160]
]
nft_power_rank_Identity_Standard = [#from Common to Limited
    #from Leader to Common solider
    [250,45,30,20,5],
    [250,67,45,30,10],
    [250,134,80,60,20],
    [250,200,135,90,30],
]
power_material_edition_addon = [#from iron to gold
    [0,0],#from standard to halloween
    [20,0],
    [50,0],
]

image_subfix = '.png'
gif_subfix = '.gif'

def unsigned_right_shitf(n,i):
    # 数字小于0，则转为32位无符号uint
    if n<0:
        n = ctypes.c_uint32(n).value
    # 正常位移位数是为正数，但是为了兼容js之类的，负数就右移变成左移好了
    if i<0:
        return - (n << abs(i))
    #print(n)
    return (n >> i)
class HFCData():
    def __init__(self) :
        #self.itemData = itemData
        self.edition,self.camp, self.rank,self.material,self.nameId,self.level,self.id,self.occu,self.end,\
            self.str,self.agi,self.arm,self.cri,self.psi,self.spi,self.pow= 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  

    
    def parseData(self,data):
        version = data & 0x3
        data = unsigned_right_shitf(data,2)

        self.material = data&0x3
        data = unsigned_right_shitf(data,2)

        self.rank = data&0x7
        data = unsigned_right_shitf(data,3)

        self.camp = data & 0x3
        data = unsigned_right_shitf(data,2)

        self.edition = data & 0xF
        data = unsigned_right_shitf(data,4)

        self.typ = data &0x7
        data = unsigned_right_shitf(data,3)
        if self.typ == 0:
            self.nameId = data & 0xFFFF
            data = unsigned_right_shitf(data,16)

            self.level = data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.id = data & 0xF
            data = unsigned_right_shitf(data,4)

            self.occu = data & 0xF
            data = unsigned_right_shitf(data,4)

            self.end = data & 0xFF
            data = unsigned_right_shitf(data,8)
            
            self.str =data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.agi =data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.arm = data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.cri = data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.psi = data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.spi = data & 0xFF
            data = unsigned_right_shitf(data,8)

            self.pow = data & 0xFFFF
            data = unsigned_right_shitf(data,16)



        return self.edition,self.camp,self.rank,self.material,self.nameId;

    def getCardName(self):
        if self.edition == 0:#standard version
            nft_name = nft_name_standard[self.camp][self.nameId-1]
        elif self.edition == 1:
            nft_name = nft_name_halloween[self.camp][self.nameId-1]
        return nft_name
    
    def getFhcImageUrl(self):
        if self.edition == 0:#standard fhc/standard/camp/rank/cardName/thumbnail/cardnameMaterial.png
           return image_prefix +nft_edition[self.edition] +'/'+ nft_camp[self.camp ] \
            +'/'+ nft_rank[self.rank] + '/' +self.getCardName()+'/Thumbnail/'+ self.getCardName() + nft_material[self.material]+image_subfix;   
        elif self.edition == 1:# fhc/haloween/camp/rank/thumbnail/cardnameMaterial.png
            return image_prefix +nft_edition[self.edition] +'/'+ nft_camp[self.camp ] \
            +'/'+ nft_rank[self.rank] +'/thumbnail/'+ self.getCardName() + nft_material[self.material]+image_subfix;

    def getFhcDetailImageUrl(self):
        if self.edition == 0:#fhc/edition/camp/rank/cardname/cardname*.png
            imageUrl = image_prefix +nft_edition[self.edition] +'/'+ nft_camp[self.camp ] \
            +'/'+ nft_rank[self.rank] + '/' +self.getCardName()+'/'+self.getCardName() + \
                nft_material[self.material] + image_subfix

        elif self.edition == 1:#fhc/haloween/camp/rank/thumbnail/cardnameMaterial
            imageUrl = image_prefix +nft_edition[self.edition] +'/'+ nft_camp[self.camp ] \
            +'/'+ nft_rank[self.rank] +'/'+self.getCardName() + nft_material[self.material]
            if self.material < 1:
                imageUrl += image_subfix
            else:
                imageUrl += gif_subfix
        return imageUrl



    def getFhcEditionValue(self):
        return nft_edition[self.edition]
    def getFhcCampValue(self):
        return nft_camp[self.camp]
    def getFhcRankValue(self):
        return nft_rank[self.rank]
    def getFhcMaterialValue(self):
        return nft_material[self.material]
    def getFhcNameValue(self):
        return self.getCardName()
    def getLevel(self):
        return self.level
    def getIdentity(self):
        return nft_identity[self.id]
    def getProfession(self):
        return nft_occupation[self.occu]
    def getEndurance(self):
        return self.end
    def getStrength(self):
        return self.str
    def getAgility(self):
        return self.agi
    def getArmor(self):
        return self.arm
    def getCrit(self):
        return self.cri
    def getPisonic(self):
        return self.psi
    def getSpirit(self):
        return self.spi
    def getPower(self):
        pow = 0 
        if self.getFhcEditionValue() == "Halloween":
            pow = nft_power_rank_material_Halloween[self.rank][self.material]
        elif self.getFhcEditionValue() =="Standard":
            pow = nft_power_rank_Identity_Standard[self.rank][self.id]+power_material_edition_addon[self.material][0]

        return pow
    def cardEncode(self, identity, occupation):
        result = self.encodeHead()

        result |= (self.nameId<<16)
        result |= (self.level<<32)
        result |= (identity<<40)
        result |= (occupation<<44)
        #Endurance
        result |= (self.end<<48)
        #Strength
        result |= (self.str<<56)
        #Agility
        result |= (self.agi<<64)
        #Armor
        result |= (self.arm<<72)
        #Crit  
        result |= (self.cri<<80)
        #Psionic
        result |= (self.psi<<88)
        #Spirit
        result |= (self.spi<<96)

        return result

    def encodeHead( self):
        result = 0
        result |= (self.material<<2)
        result |= (self.rank<<4)
        result |= (self.camp<<7)
        result |= (self.edition<<9)
        result |= (self.typ<<13)
        return result
'''
hfc = HFCData()
hfc.parseData(1917589937487742446536691089544)
print(hfc.getFhcCampValue(),hfc.getFhcNameValue(),hfc.getIdentity(),hfc.getFhcRankValue(),hfc.getFhcMaterialValue(),hfc.getPower())
print(hfc.cardEncode(0,0))
print(hfc.cardEncode(1,1))
print(hfc.cardEncode(1,2))
print(hfc.cardEncode(1,3))
print(hfc.cardEncode(1,4))
'''