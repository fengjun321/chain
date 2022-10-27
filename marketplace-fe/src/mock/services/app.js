import { getData, getRecords } from '../utils';

export default {
  'GET /api/v1/marketplace/tradingMarket': ({ url, type, body }) => {
    // 22-03-26: 已废弃
    // console.log(`mock:[${type}] ${url}`);
    const urlParmas = {}
    url.split("?")[1].split("&").forEach(elem => {
      const [key, value] = elem.split('=')
      urlParmas[key] = value
    });
    console.log(urlParmas);

    // egg
    if (urlParmas.nftType === '8') {
      return getRecords([
        {
          "tokenId": "1",
          "name": "FPE(Pet Egg of Spark Era) #1",
          "image": "https://intro.firework.games/fpe/PetEgg-AsmanZerg.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-AsmanZerg.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "epic",
          "price": "11",
          "attributes": [
            {
              "trait_type": "type",
              "value": "AsmanZerg"
            }
          ]
        },
        {
          "tokenId": "2",
          "name": "FPE(Pet Egg of Spark Era) #2",
          "image": "https://intro.firework.games/fpe/PetEgg-EmpireofAmda.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-EmpireofAmda.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "epic",
          "price": "11",
          "attributes": [
            {
              "trait_type": "type",
              "value": "EmpireofAmda"
            }
          ]
        },
        {
          "tokenId": "3",
          "name": "FPE(Pet Egg of Spark Era) #3",
          "image": "https://intro.firework.games/fpe/PetEgg-ConfederationofEarth.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-ConfederationofEarth.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "epic",
          "price": "11",
          "attributes": [
            {
              "trait_type": "type",
              "value": "ConfederationofEarth"
            }
          ]
        },
        {
          "tokenId": "4",
          "name": "FPE(Pet Egg of Spark Era) #4",
          "image": "https://intro.firework.games/fpe/PetEgg-EternalProtoss.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-EternalProtoss.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "epic",
          "price": "11",
          "attributes": [
            {
              "trait_type": "type",
              "value": "EternalProtoss"
            }
          ]
        },
      ], 4)
    }

    // material
    if (urlParmas.nftType === '7') {
      return getRecords([
        {
          "tokenId": "1",
          "name": "material #1",
          "image": "https://intro.firework.games/fpe/PetEgg-AsmanZerg.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-AsmanZerg.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "AsmanZerg"
            }
          ]
        },
        {
          "tokenId": "2",
          "name": "material #2",
          "image": "https://intro.firework.games/fpe/PetEgg-EmpireofAmda.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-EmpireofAmda.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "EmpireofAmda"
            }
          ]
        },
        {
          "tokenId": "3",
          "name": "material #3",
          "image": "https://intro.firework.games/fpe/PetEgg-ConfederationofEarth.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-ConfederationofEarth.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "ConfederationofEarth"
            }
          ]
        },
        {
          "tokenId": "4",
          "name": "material #4",
          "image": "https://intro.firework.games/fpe/PetEgg-EternalProtoss.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-EternalProtoss.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "EternalProtoss"
            }
          ]
        },
        {
          "tokenId": "5",
          "name": "material #5",
          "image": "https://intro.firework.games/fpe/PetEgg-AsmanZerg.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-AsmanZerg.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "AsmanZerg"
            }
          ]
        },
        {
          "tokenId": "6",
          "name": "material #6",
          "image": "https://intro.firework.games/fpe/PetEgg-EmpireofAmda.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-EmpireofAmda.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "EmpireofAmda"
            }
          ]
        },
        {
          "tokenId": "7",
          "name": "material #7",
          "image": "https://intro.firework.games/fpe/PetEgg-ConfederationofEarth.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-ConfederationofEarth.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "ConfederationofEarth"
            }
          ]
        },
        {
          "tokenId": "8",
          "name": "material #8",
          "image": "https://intro.firework.games/fpe/PetEgg-EternalProtoss.png",
          "detailimage": "https://intro.firework.games/fpe/PetEgg-EternalProtoss.png",
          "description": "Pet Egg of Spark Era.",
          "rarity": "common",
          "price": "77",
          "attributes": [
            {
              "trait_type": "type",
              "value": "EternalProtoss"
            }
          ]
        },
      ], 4)
    }

    // equipment
    if (urlParmas.nftType === '5') {
      return getRecords(
        [
          {
            "favorites": 1,
            "occupation": 0,
            "strength": 31,
            "tokenURI": "https://api.firework.games/fhc/metadata/71/3488218943513511731314803081236",
            "description": "Firework is game-dev platform.",
            "type": 0,
            "endurance": 61,
            "psionic": 7,
            "identity": 3,
            "price": 40,
            "power": 50,
            "attrName": "Barnes",
            "views": 11,
            "image": "https://intro.firework.games/fhc/Standard/AsmanZerg/Rare/Barnes/Thumbnail/BarnesSilver.png",
            "tokenId": 71,
            "level": 1,
            "releaseVersion": 0,
            "attrMaterial": "Silver",
            "spirit": 44,
            "attrEdition": "Standard",
            "version": 0,
            "camp": 0,
            "attrCamp": "AsmanZerg",
            "totalAttrs": 200,
            "armor": 21,
            "material": 1,
            "crit": 11,
            "itemData": "3488218943513511731314803081236",
            "name": "FHC(Firework Hero Card) #71",
            "nameId": 12,
            "attrRank": "Rare",
            "ownerAddress": "0x876b67fa46d2e78a22e5fc2979acedbcae8ee4b7",
            "agility": 25,
            "rarity": 1,
            "isFavorite": 0,
            "isOnwer": false
          },
          {
            "favorites": 0,
            "occupation": 3,
            "strength": 15,
            "tokenURI": "https://api.firework.games/fhc/metadata/46/3647601447371255582137686360320",
            "description": "Firework is game-dev platform.",
            "type": 0,
            "endurance": 45,
            "psionic": 10,
            "identity": 3,
            "price": 50,
            "power": 20,
            "attrName": "Lopes",
            "views": 24,
            "image": "https://intro.firework.games/fhc/Standard/ConfederationofEarth/Common/Lopes/Thumbnail/LopesDarkIron.png",
            "tokenId": 46,
            "level": 1,
            "releaseVersion": 0,
            "attrMaterial": "DarkIron",
            "spirit": 46,
            "attrEdition": "Standard",
            "version": 0,
            "camp": 2,
            "attrCamp": "ConfederationofEarth",
            "totalAttrs": 200,
            "armor": 51,
            "material": 0,
            "crit": 9,
            "itemData": "3647601447371255582137686360320",
            "name": "FHC(Firework Hero Card) #46",
            "nameId": 7,
            "attrRank": "Common",
            "ownerAddress": "0x9441e7b269138812f1f9898dc05a6371c3843cf9",
            "agility": 24,
            "rarity": 0,
            "isFavorite": 0,
            "isOnwer": false
          },
          {
            "favorites": 0,
            "occupation": 4,
            "strength": 38,
            "tokenURI": "https://api.firework.games/fhc/metadata/48/6267403927281675974479685747124",
            "description": "Firework is game-dev platform.",
            "type": 0,
            "endurance": 22,
            "psionic": 27,
            "identity": 3,
            "price": 110,
            "power": 110,
            "attrName": "Alario",
            "views": 6,
            "image": "https://intro.firework.games/fhc/Standard/EternalProtoss/Legend/Alario/Thumbnail/AlarioSilver.png",
            "tokenId": 48,
            "level": 1,
            "releaseVersion": 0,
            "attrMaterial": "Silver",
            "spirit": 79,
            "attrEdition": "Standard",
            "version": 0,
            "camp": 3,
            "attrCamp": "EternalProtoss",
            "totalAttrs": 200,
            "armor": 5,
            "material": 1,
            "crit": 19,
            "itemData": "6267403927281675974479685747124",
            "name": "FHC(Firework Hero Card) #48",
            "nameId": 10,
            "attrRank": "Legend",
            "ownerAddress": "0x56ee1f2c4153f861c950fa1e74c98aa17b0dc898",
            "agility": 10,
            "rarity": 3,
            "isFavorite": 0,
            "isOnwer": true
          }
        ])
    }

    // weapon
    if (urlParmas.nftType === '6') {
      return getRecords(
        [
          {
            "favorites": 1,
            "occupation": 0,
            "strength": 31,
            "tokenURI": "https://api.firework.games/fhc/metadata/71/3488218943513511731314803081236",
            "description": "Firework is game-dev platform.",
            "type": 0,
            "endurance": 61,
            "psionic": 7,
            "identity": 3,
            "price": 40,
            "power": 50,
            "attrName": "Barnes",
            "views": 11,
            "image": "https://intro.firework.games/fhc/Standard/AsmanZerg/Rare/Barnes/Thumbnail/BarnesSilver.png",
            "tokenId": 71,
            "level": 1,
            "releaseVersion": 0,
            "attrMaterial": "Silver",
            "spirit": 44,
            "attrEdition": "Standard",
            "version": 0,
            "camp": 0,
            "attrCamp": "AsmanZerg",
            "totalAttrs": 200,
            "armor": 21,
            "material": 1,
            "crit": 11,
            "itemData": "3488218943513511731314803081236",
            "name": "FHC(Firework Hero Card) #71",
            "nameId": 12,
            "attrRank": "Rare",
            "ownerAddress": "0x876b67fa46d2e78a22e5fc2979acedbcae8ee4b7",
            "agility": 25,
            "rarity": 1,
            "isFavorite": 0,
            "isOnwer": false
          },
          {
            "favorites": 0,
            "occupation": 3,
            "strength": 15,
            "tokenURI": "https://api.firework.games/fhc/metadata/46/3647601447371255582137686360320",
            "description": "Firework is game-dev platform.",
            "type": 0,
            "endurance": 45,
            "psionic": 10,
            "identity": 3,
            "price": 50,
            "power": 20,
            "attrName": "Lopes",
            "views": 24,
            "image": "https://intro.firework.games/fhc/Standard/ConfederationofEarth/Common/Lopes/Thumbnail/LopesDarkIron.png",
            "tokenId": 46,
            "level": 1,
            "releaseVersion": 0,
            "attrMaterial": "DarkIron",
            "spirit": 46,
            "attrEdition": "Standard",
            "version": 0,
            "camp": 2,
            "attrCamp": "ConfederationofEarth",
            "totalAttrs": 200,
            "armor": 51,
            "material": 0,
            "crit": 9,
            "itemData": "3647601447371255582137686360320",
            "name": "FHC(Firework Hero Card) #46",
            "nameId": 7,
            "attrRank": "Common",
            "ownerAddress": "0x9441e7b269138812f1f9898dc05a6371c3843cf9",
            "agility": 24,
            "rarity": 0,
            "isFavorite": 0,
            "isOnwer": false
          },
          {
            "favorites": 0,
            "occupation": 4,
            "strength": 38,
            "tokenURI": "https://api.firework.games/fhc/metadata/48/6267403927281675974479685747124",
            "description": "Firework is game-dev platform.",
            "type": 0,
            "endurance": 22,
            "psionic": 27,
            "identity": 3,
            "price": 110,
            "power": 110,
            "attrName": "Alario",
            "views": 6,
            "image": "https://intro.firework.games/fhc/Standard/EternalProtoss/Legend/Alario/Thumbnail/AlarioSilver.png",
            "tokenId": 48,
            "level": 1,
            "releaseVersion": 0,
            "attrMaterial": "Silver",
            "spirit": 79,
            "attrEdition": "Standard",
            "version": 0,
            "camp": 3,
            "attrCamp": "EternalProtoss",
            "totalAttrs": 200,
            "armor": 5,
            "material": 1,
            "crit": 19,
            "itemData": "6267403927281675974479685747124",
            "name": "FHC(Firework Hero Card) #48",
            "nameId": 10,
            "attrRank": "Legend",
            "ownerAddress": "0x56ee1f2c4153f861c950fa1e74c98aa17b0dc898",
            "agility": 10,
            "rarity": 3,
            "isFavorite": 0,
            "isOnwer": true
          }
        ])
    }

    return getRecords([
      { name: 'name', value: 'value' },
      { name: 'name', value: 'value' },
    ], 99)
  }
};
