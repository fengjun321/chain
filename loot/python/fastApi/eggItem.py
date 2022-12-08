import json
import ctypes


image_prefix = 'https://intro.firework.games/fpe/PetEgg-'

nft_camp=[
    'AsmanZerg',
    'EmpireofAmda',
    'ConfederationofEarth',
    'EternalProtoss',
]




image_subfix = '.png'

def getEggImageUrl(data):
    return image_prefix + nft_camp[data ] + image_subfix;

def getEggTraitValue(data):
    return nft_camp[data ]
'''
hfc = HFCData()
hfc.parseData(1917589937487742446536691089544)
print(hfc.getFhcCampValue(),hfc.getFhcNameValue(),hfc.getIdentity(),hfc.getFhcRankValue(),hfc.getFhcMaterialValue(),hfc.getPower())
'''