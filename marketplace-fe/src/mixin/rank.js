import constant from "../common/constant";

export default {
  data() {
    return {
      constant: constant,
      list: [],
      cardType: -1,
      camp: -1,
      rarity: -1,
      pseType: -1,
      egseType: -1,
      spaceShipType: -1,
      spaceShipLevel: -1,
      spaceshipLevelOption: [
        {
          key: -1,
          value: "ALL",
        },
      ],
      cardTypeOption: [
        {
          key: -1,
          value: "ALL",
        },
        {
          key: constant.cardType.card,
          value: "Hero Card",
        },
        {
          key: constant.cardType.egse,
          value: "EGSE",
        },
        {
          key: constant.cardType.pse,
          value: "PSE",
        },
        {
          key: constant.cardType.halloween,
          value: "HSE",
        },
        {
          key: constant.cardType.spaceship,
          value: "SPACESHIP",
        },
        {
          key: constant.cardType.egg,
          value: "egg",
        },
        {
          key: constant.cardType.lootbox,
          value: "lootbox",
        },
        {
          key: constant.cardType.material,
          value: "material",
        },
        {
          key: constant.cardType.equipment,
          value: "equipment",
        },
      ],
      campOption: [
        {
          key: -1,
          value: "ALL",
        },
      ],
      rarityOption: [
        {
          key: -1,
          value: "ALL",
        },
      ],
      pseTypeOption: [
        {
          key: -1,
          value: "ALL",
        },
      ],
      egseTypeOption: [
        {
          key: -1,
          value: "ALL",
        },
      ],
      spaceShipTypeOption: [
        {
          key: -1,
          value: "ALL",
        },
      ],
    };
  },
  created() {
    this.updateVm();
    this.$watch(
      () =>
        this.cardType +
        this.camp +
        this.rarity +
        this.pseType +
        this.egseType +
        this.spaceShipType +
        this.spaceShipLevel,
      () => {
        // console.log("watch: optionChange");
        this.updateUrl();
      }
    );
  },
  computed: {
    nftTypeIs() {
      return type => {
        if (typeof type === 'string' || typeof type === 'number') {
          return this.cardType === constant.cardType[type]
        } else if (Array.isArray(type)) {
          return type.some(t => this.cardType === constant.cardType[t])
        }
        // edge case
        return false
      }
    },
  },
  methods: {
    updateVm() {
      const { flag, ...query } = this.$route.query;
      const list = [
        "cardType",
        "camp",
        "rarity",
        "pseType",
        "egseType",
        "spaceShipType",
        "spaceShipLevel",
      ];
      list.forEach((key) => {
        if (query[key]) {
          if (key === "cardType") {
            this[key] = query[key] - 0;
          } else {
            this[key] = query[key];
          }
        }
      });
    },
    updateUrl() {
      const { flag, ...query } = this.$route.query;
      const list = [
        "cardType",
        "camp",
        "rarity",
        "pseType",
        "egseType",
        "spaceShipType",
        "spaceShipLevel",
      ];
      list.forEach((key) => {
        if (this[key] !== -1) {
          query[key] = this[key];
        } else {
          delete query[key];
        }
      });

      this.$router.replace({
        path: this.$route.path,
        query: { flag, ...query },
      });
    },
    init() {
      for (const ele in constant.camp) {
        this.campOption.push({
          key: ele,
          value: constant.camp[ele],
        });
      }
      for (const ele in constant.pseType) {
        this.pseTypeOption.push({
          key: ele,
          value: constant.pseType[ele],
        });
      }
      for (const ele in constant.egseType) {
        this.egseTypeOption.push({
          key: ele,
          value: constant.egseType[ele],
        });
      }
      for (const ele in constant.rarity) {
        this.rarityOption.push({
          key: ele,
          value: constant.rarity[ele],
        });
      }
      for (const ele in constant.spaceshipType) {
        this.spaceShipTypeOption.push({
          key: ele,
          value: constant.spaceshipType[ele],
        });
      }
      for (let i = 1; i < 10; i++) {
        this.spaceshipLevelOption.push({
          key: i,
          value: i,
        });
      }
    },
    getQueryParameter() {
      let query = {
        pageSize: 100,
      };
      if (this.cardType === constant.cardType.card) {
        if (this.camp !== -1) {
          query.camp = this.camp;
        }
        if (this.rarity !== -1) {
          query.rarity = this.rarity;
        }
      }
      if (this.cardType === constant.cardType.pse) {
        if (this.pseType !== -1) {
          query.camp = this.pseType;
        }
      }
      if (this.cardType === constant.cardType.egse) {
        if (this.egseType !== -1) {
          query.camp = this.egseType;
        }
      }
      if (this.cardType === constant.cardType.halloween) {
      }
      if (this.cardType === constant.cardType.spaceship) {
        if (this.spaceShipType !== -1) {
          query.rank = this.spaceShipTypeOption[this.spaceShipType].value;
        }
        if (this.spaceShipLevel !== -1) {
          query.level = this.spaceShipLevel;
        }
      }
      if (this.cardType !== -1) {
        query.nftType = this.cardType;
      }
      return query;
    },
    onCampChange(e) {
      this.camp = e;
      this.load();
    },
    onCardChange(e) {
      this.cardType = e;
      this.egseType = -1;
      this.pseType = -1;
      this.camp = -1;
      this.rarity = -1;
      this.spaceShipType = -1;
      this.spaceShipLevel = -1;
      this.load();
    },
    onRarityChange(e) {
      this.rarity = e;
      this.load();
    },
    onPseChange(e) {
      this.pseType = e;
      this.load();
    },
    onEgseChange(e) {
      this.egseType = e;
      this.load();
    },
    onSpaceShipTypeChange(e) {
      this.spaceShipType = e;

      this.load();
    },
    spaceShipLevelChange(e) {
      this.spaceShipLevel = e;
      this.load();
    },
  },
};
