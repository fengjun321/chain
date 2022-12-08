import json

image_prefix = 'https://intro.firework.games/hse/'
nft_name=[
    'halloween'
    ];
image_subfix = '.png'
block_name = 'blacklist'

def getHseImageUrl(data):
    if data != 0xfe:
        return image_prefix + nft_name[data ] + image_subfix
    else:
        return image_prefix + block_name + image_subfix

def getHseTraitValue(data):
    if data != 0xfe:
        return nft_name[data ]
    else:
        return block_name