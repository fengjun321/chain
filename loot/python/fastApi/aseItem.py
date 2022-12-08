import json
import ctypes
import base64

image_prefix = 'https://intro.firework.games/ase/'
nft_name = [
            'ProtectorHelmet','EnergyCuirass','ProtectorLeggings','ProtectorCuirass','KandalsHelmet','KandalsCuirass',
            'KandalsLeggings','SpectreCuirass','MaraudersCuirass','MaraudersLeggings','MaraudersHelmet','Multi-thread','Lacerator',
            'MaraudersRifle','MaraudersPistol','KandalsSword','KandalsSharpClaws'
    ]

nft_rank = ['Rare','Common','Common','Rare','Legendary','Legendary','Epic','Epic','Legendary','Epic','Legendary','Rare','Common','Legendary','Legendary','Epic','Legendary']
nft_power = [85,26,15,45,180,110,75,85,130,85,200,55,15,135,115,95,120]

nft_desc = [
    'A relatively common combat equipment, dropped in the Instanced Dungeon. It is a combat equipment that is widely purchased by interstellar traders, and it is also the hard currency of interstellar trade during the war.',
    'A relatively common combat equipment, dropped in the Instanced Dungeon. It is a combat equipment that is widely purchased by interstellar traders, and it is also the hard currency of interstellar trade during the war.',
    'A relatively common combat equipment, dropped in the Instanced Dungeon. It is a combat equipment that is widely purchased by interstellar traders, and it is also the hard currency of interstellar trade during the war.',
    'A relatively common combat equipment, dropped in the Instanced Dungeon. It is a combat equipment that is widely purchased by interstellar traders, and it is also the hard currency of interstellar trade during the war.',
    'A superb piece of equipment,dropped in the Forgotten Realm: The Wrath of Kadal.  and its excellent combat bonus attributes make all adventurers flock to it.',
    'A superb piece of equipment,dropped in the Forgotten Realm: The Wrath of Kadal.  and its excellent combat bonus attributes make all adventurers flock to it.',
    'A superb piece of equipment,dropped in the Forgotten Realm: The Wrath of Kadal.  and its excellent combat bonus attributes make all adventurers flock to it.',
    'A superb piece of equipment,dropped in the Forgotten Realm: The Wrath of Kadal.  and its excellent combat bonus attributes make all adventurers flock to it.',
    'A commemorative piece of equipment,dropped in V3-Galaxy Loot Box.It provides extremely good combat attributes, and its appearance is also very collectible.',
    'A commemorative piece of equipment,dropped in V3-Galaxy Loot Box.It provides extremely good combat attributes, and its appearance is also very collectible.',
    'A commemorative piece of equipment,dropped in V3-Galaxy Loot Box.It provides extremely good combat attributes, and its appearance is also very collectible.',
    'A relatively common combat equipment, dropped in the Instanced Dungeon. It is a combat equipment that is widely purchased by interstellar traders, and it is also the hard currency of interstellar trade during the war.',
    'A relatively common combat equipment, dropped in the Instanced Dungeon. It is a combat equipment that is widely purchased by interstellar traders, and it is also the hard currency of interstellar trade during the war.',
    'A commemorative piece of equipment,dropped in V3-Galaxy Loot Box.It provides extremely good combat attributes, and its appearance is also very collectible.',
    'A commemorative piece of equipment,dropped in V3-Galaxy Loot Box.It provides extremely good combat attributes, and its appearance is also very collectible.',
    'A superb piece of equipment,dropped in the Forgotten Realm: The Wrath of Kadal. and its excellent combat bonus attributes make all adventurers flock to it.',
    'A superb piece of equipment,dropped in the Forgotten Realm: The Wrath of Kadal. and its excellent combat bonus attributes make all adventurers flock to it.ot Box.It provides extremely good combat attributes, and its appearance is also very collectible.'
]
nft_attr_matrix=[#endurance/strength/agility/armor/crit/psionic/spirit
    [3,0,0,5,0,0,5],
    [10,0,0,0,0,0,0],
    [5,0,0,3,0,0,3],
    [8,0,0,5,0,0,5],
    [10,10,10,10,10,10,10],
    [12,0,0,12,5,0,10],
    [5,0,5,5,0,0,5],
    [5,0,5,0,8,0,5],
    [10,10,10,10,10,10,10],
    [5,5,5,5,5,5,5],
    [18,18,18,18,18,18,18],
    [0,5,0,0,0,5,0],
    [0,2,0,0,0,3,0],
    [0,25,10,0,10,15,0],
    [0,8,5,0,5,8,0],
    [0,3,8,0,12,15,0],
    [0,10,8,0,8,10,0]
]


class AseData():
    def __init__(self) :
        self.nameId = 0
    def parseData(self,data):
        self.nameId = data
    
    def getName(self):
        return nft_name[self.nameId - 1]
    def getRank(self):
        return nft_rank[self.nameId -1]

    def getImageUrl(self):
        return image_prefix +self.getName()+'.png'
   
    def getDesc(self):
        return nft_desc[self.nameId - 1]
    
    def getPower(self):
        return nft_power[self.nameId-1]
    
    def getEndurance(self):
        return nft_attr_matrix[self.nameId -1][0]
    def getStrength(self):
        return nft_attr_matrix[self.nameId -1][1]
    def getAgility(self):
        return nft_attr_matrix[self.nameId -1][2]
    def getArmor(self):
        return nft_attr_matrix[self.nameId -1][3]
    def getCrit(self):
        return nft_attr_matrix[self.nameId -1][4]
    def getPisonic(self):
        return nft_attr_matrix[self.nameId -1][5]
    def getSpirit(self):
        return nft_attr_matrix[self.nameId -1][6]

