import json

image_prefix = 'https://intro.firework.games/mbse/'

image_subfix = '.png'

galaxy_name = [
    "BSC",
    "ETH",
    "BTC"
]

galaxy_desc = [
    'Desc of BSC',
    'Desc of ETH',
    'Desc of BTC'
]
galaxy_level_min = [1,4,7]
galaxy_level_max = [3,6,8]
planet_name = [
    'Planet Minor','Planet Medium','Planet Senior','Planet SSR',
    'Planet Minor','Planet Medium','Planet Senior','Planet SSR',
    'Planet Minor','Planet Medium','Planet Senior','Planet SSR',
]
planet_desc = [
    'desc of planet minor','desc of planet medium','plannet of senior','desc of ssr',
    'desc of planet minor','desc of planet medium','plannet of senior','desc of ssr',
    'desc of planet minor','desc of planet medium','plannet of senior','desc of ssr'
]

planet_level_min = [
        0,4,7,9,
        1,4,7,9,
        1,4,7,9,
]
planet_level_max = [
        10,10,10,10,
        10,10,10,10,
        10,10,10,10
]
galaxy_planet_matrix = [
    [1,2,3,4],
    [5,6,7,8],
    [9,10,11,12]
]
planet_galaxy_matrix = [1,1,1,1,2,2,2,2,3,3,3,3]
def getGalaxyName(id):
    return galaxy_name[id -1 ]

def getGalaxyMaxLevel(id):
    return galaxy_level_max[id -1 ]

def getGalaxyMinLevel(id):
    return galaxy_level_min[id -1 ]
def getGalaxyDesc(id):
    return galaxy_desc[id -1 ]

def getGalaxyByPlanetId(planetId):
    return planet_galaxy_matrix[planetId - 1]
def getGalaxy(id):
    galaxy = {
        "id":id,
        "name":getGalaxyName(id),
        "image":getGalaxyImg(id),
        "detailImage":getGalaxyImg(id),
        "description":getGalaxyDesc(id),
        "plannets": getGalaxyPlannets(id),
    }
    return galaxy
def getAllGalaxy():
    galaxy_list = []
    for id in range(1,len(galaxy_name)+1):
        galaxy = getGalaxy(id)
        galaxy_list.append(galaxy)
    return galaxy_list

def getGalaxyPlannets(id):
    plannet_list = []
    for index in range(0,len(galaxy_planet_matrix[id -1])):
        planet_obj = getPlanet(galaxy_planet_matrix[id -1][index])
        plannet_list.append(planet_obj)
    return plannet_list

def getPlanet(planet_id):
    galaxy_id = getGalaxyByPlanetId(planet_id)
    planet_obj = {
            "id":planet_id,
            "galaxyId":galaxy_id,
            "name":getPlanetName(planet_id),
            "image":getPlanetImg(planet_id),
            "detailImage":getPlanetImg(planet_id),
            "description":getPlanetDesc(planet_id),
            "cardMaxLevel":getPlanetMaxLevel(planet_id),
            "cardMinLevel":getPlanetMinLevel(planet_id),
        }
    return planet_obj
def getPlanetName(planet_id):
    return planet_name[planet_id -1 ]

def getPlanetMaxLevel(planet_id):
    return planet_level_max[planet_id -1 ]

def getPlanetMinLevel(planet_id):
    return planet_level_min[planet_id -1 ] 

def getPlanetDesc(planet_id):
    return planet_desc[planet_id -1 ] 

def getGalaxyImg(id):
    return image_prefix + galaxy_name[id -1]+ image_subfix
def getPlanetImg(planet_id):
    return image_prefix + planet_name[planet_id -1]+ image_subfix

