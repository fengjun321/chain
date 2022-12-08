import json

image_prefix = 'https://intro.firework.games/egse/'
nft_name=[
    'normal',
    'rare',
    'epic',
    ];
image_subfix = '.png'

def getEgseImageUrl(data):
    return image_prefix + nft_name[data ] + image_subfix;

def getEgseTraitValue(data):
    return nft_name[data ]
