import json
import ctypes


image_prefix = 'https://intro.firework.games/items/'

nft_items=[
    'Stardust',
    'CorruptorsSlime',
    'BlastingEssence',
    'CorruptorsHeart',
    'EssenceofFire',
    'SparkDew',
    'MaraudersFragments'
]
nft_desc = [
    'The most extensive basic substance in the universe, it is widely used to upgrade spaceships and strengthen warriors.',
    'A common material dropped in Kandar Corruptor, a necessary material for strengthening heroes, the violent chemical reaction will make people lose their minds.',
    'A rare material dropped in Kandar Blasting Bugs, the huge energy generated from the fission explosion also brings hope for life.',
    'A common material dropped in Kandar Silver Star Corruptor, a necessary material for strengthening heroes, the violent chemical reaction will make people lose their minds. “Increase the dose, I need more powerful stimulation!”',
    'The life essence dropped in Fire Demon of the Kandar Instance is a noble tribute to the flame, and is widely used in the strengthening of living beings.',
    'An extremely precious cosmic dust substance, condensed into essence by ancient time, has the nickname of Cosmic Tears. It is widely used in the manufacture of precision instruments and in higher-strength intelligent equipment.',
    'Collect all looter fragments to exchange for the corresponding looter set!',
]
nft_rank = ['common','common','Rare','Rare','Epic','Legend','Legend']



image_subfix = '.jpg'
mp4_subfix = '.mp4'

def getGameItemsImageUrl(data):
    return image_prefix + nft_items[data ]+'/'+nft_items[data ] + image_subfix
def getGameItemsDetailUrl(data):
    return image_prefix + nft_items[data ]+'/'+nft_items[data ] + mp4_subfix

def getItemDesc(data):
    return nft_desc[data ]

def getItemRank(data):
    return nft_rank[data ]
'''
hfc = HFCData()
hfc.parseData(1917589937487742446536691089544)
print(hfc.getFhcCampValue(),hfc.getFhcNameValue(),hfc.getIdentity(),hfc.getFhcRankValue(),hfc.getFhcMaterialValue(),hfc.getPower())
'''