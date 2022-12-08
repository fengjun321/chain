import json

image_prefix = 'https://intro.firework.games/mbse/'
nft_name=[
    'EgseNormal',
    'EgseRare',
    'EgseEpic',
    'Standard',
    'Permium',
    'Epic',
    'Legendary',
    'Collection',
    'Identity',
    'Identity',
    'Identity',
    'Identity',
    'Faction',
    'Faction',
    'Faction',
    'Faction',
    'Spaceship',
    'V3Galaxy',
    'Kadal'
    ]
image_subfix = '.png'

def getMbseImageUrl(data):
    return image_prefix + nft_name[data ] + image_subfix

def getMbseTraitValue(data):
    return nft_name[data ]
