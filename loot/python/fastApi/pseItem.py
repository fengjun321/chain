import json

image_prefix = 'https://intro.firework.games/passport/'
passport_name=[
    'typeI',
    'typeII',
    'stars',
    'galxy',
    'eternal',
    'blackbox',
    'golddigger',
    'trabilizer',
    ];
image_subfix = '.png'

def getImageUrl(data):
    return image_prefix + passport_name[data ] + image_subfix;

def getTraitValue(data):
    return passport_name[data ]
def getClaimed(claimed):
    if claimed == 1:
        return True
    else:
        return False

