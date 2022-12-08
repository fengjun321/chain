/**
 *Submitted for verification at BscScan.com on 2021-05-12
*/

/**
 *Submitted for verification at hecoinfo.com on 2021-03-03
*/

pragma solidity ^0.5.8;


library SafeMath {
    /**
     * @dev Returns the addition of two unsigned integers, reverting on
     * overflow.
     *
     * Counterpart to Solidity's `+` operator.
     *
     * Requirements:
     * - Addition cannot overflow.
     */
    function add(uint256 a, uint256 b) internal pure returns (uint256) {
        uint256 c = a + b;
        require(c >= a, "SafeMath: addition overflow");

        return c;
    }

    /**
     * @dev Returns the subtraction of two unsigned integers, reverting on
     * overflow (when the result is negative).
     *
     * Counterpart to Solidity's `-` operator.
     *
     * Requirements:
     * - Subtraction cannot overflow.
     */
    function sub(uint256 a, uint256 b) internal pure returns (uint256) {
        return sub(a, b, "SafeMath: subtraction overflow");
    }

    /**
     * @dev Returns the subtraction of two unsigned integers, reverting with custom message on
     * overflow (when the result is negative).
     *
     * Counterpart to Solidity's `-` operator.
     *
     * Requirements:
      * - Subtraction cannot overflow.
     */
    function sub(uint256 a, uint256 b, string memory errorMessage) internal pure returns (uint256) {
        require(b <= a, errorMessage);
        uint256 c = a - b;

        return c;
    }

    /**
     * @dev Returns the multiplication of two unsigned integers, reverting on
     * overflow.
     *
     * Counterpart to Solidity's `*` operator.
     *
     * Requirements:
     * - Multiplication cannot overflow.
     */
    function mul(uint256 a, uint256 b) internal pure returns (uint256) {
        // Gas optimization: this is cheaper than requiring 'a' not being zero, but the
        // benefit is lost if 'b' is also tested.
        // See: https://github.com/OpenZeppelin/openzeppelin-contracts/pull/522
        if (a == 0) {
            return 0;
        }

        uint256 c = a * b;
        require(c / a == b, "SafeMath: multiplication overflow");

        return c;
    }

    /**
     * @dev Returns the integer division of two unsigned integers. Reverts on
     * division by zero. The result is rounded towards zero.
     *
     * Counterpart to Solidity's `/` operator. Note: this function uses a
     * `revert` opcode (which leaves remaining gas untouched) while Solidity
     * uses an invalid opcode to revert (consuming all remaining gas).
     *
     * Requirements:
     * - The divisor cannot be zero.
     */
    function div(uint256 a, uint256 b) internal pure returns (uint256) {
        return div(a, b, "SafeMath: division by zero");
    }

    /**
     * @dev Returns the integer division of two unsigned integers. Reverts with custom message on
     * division by zero. The result is rounded towards zero.
     *
     * Counterpart to Solidity's `/` operator. Note: this function uses a
     * `revert` opcode (which leaves remaining gas untouched) while Solidity
     * uses an invalid opcode to revert (consuming all remaining gas).
     *
     * Requirements:
     * - The divisor cannot be zero.
     */
    function div(uint256 a, uint256 b, string memory errorMessage) internal pure returns (uint256) {
        // Solidity only automatically asserts when dividing by 0
        require(b > 0, errorMessage);
        uint256 c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold

        return c;
    }

    /**
     * @dev Returns the remainder of dividing two unsigned integers. (unsigned integer modulo),
     * Reverts when dividing by zero.
     *
     * Counterpart to Solidity's `%` operator. This function uses a `revert`
     * opcode (which leaves remaining gas untouched) while Solidity uses an
     * invalid opcode to revert (consuming all remaining gas).
     *
     * Requirements:
     * - The divisor cannot be zero.
     */
    function mod(uint256 a, uint256 b) internal pure returns (uint256) {
        return mod(a, b, "SafeMath: modulo by zero");
    }

    /**
     * @dev Returns the remainder of dividing two unsigned integers. (unsigned integer modulo),
     * Reverts with custom message when dividing by zero.
     *
     * Counterpart to Solidity's `%` operator. This function uses a `revert`
     * opcode (which leaves remaining gas untouched) while Solidity uses an
     * invalid opcode to revert (consuming all remaining gas).
     *
     * Requirements:
     * - The divisor cannot be zero.
     */
    function mod(uint256 a, uint256 b, string memory errorMessage) internal pure returns (uint256) {
        require(b != 0, errorMessage);
        return a % b;
    }
}

interface ERC721
{
  event Transfer(
    address indexed _from,
    address indexed _to,
    uint256 indexed _tokenId
  );

  event Approval(
    address indexed _owner,
    address indexed _approved,
    uint256 indexed _tokenId
  );


  event ApprovalForAll(
    address indexed _owner,
    address indexed _operator,
    bool _approved
  );

  function safeTransferFrom(
    address _from,
    address _to,
    uint256 _tokenId,
    bytes calldata _data
  )
    external;
    
  function safeTransferFrom(
    address _from,
    address _to,
    uint256 _tokenId
  )
    external;
    
  function transferFrom(
    address _from,
    address _to,
    uint256 _tokenId
  )
    external;

  function approve(
    address _approved,
    uint256 _tokenId
  )
    external;
    
  function setApprovalForAll(
    address _operator,
    bool _approved
  )
    external;

  function balanceOf(
    address _owner
  )
    external
    view
    returns (uint256);

  function ownerOf(
    uint256 _tokenId
  )
    external
    view
    returns (address);

  function getApproved(
    uint256 _tokenId
  )
    external
    view
    returns (address);

  function isApprovedForAll(
    address _owner,
    address _operator
  )
    external
    view
    returns (bool);

}

library Address {
    function isContract(address account) internal view returns (bool) {
        uint256 size;
        // solhint-disable-next-line no-inline-assembly
        assembly { size := extcodesize(account) }
        return size > 0;
    }

    function sendValue(address payable recipient, uint256 amount) internal {
        require(address(this).balance >= amount, "Address: insufficient balance");

        // solhint-disable-next-line avoid-call-value
        (bool success, ) = recipient.call.value(amount)("");
        require(success, "Address: unable to send value, recipient may have reverted");
    }


    function toPayable(address account) internal pure returns (address payable) {
        return address(uint160(account));
    }

    function functionStaticCall(address target, bytes memory data) internal view returns (bytes memory) {
        return functionStaticCall(target, data, "Address: low-level static call failed");
    }

    function functionStaticCall(address target, bytes memory data, string memory errorMessage) internal view returns (bytes memory) {
        require(isContract(target), "Address: static call to non-contract");

        // solhint-disable-next-line avoid-low-level-calls
        (bool success, bytes memory returndata) = target.staticcall(data);
        return _verifyCallResult(success, returndata, errorMessage);
    }

    function functionDelegateCall(address target, bytes memory data) internal returns (bytes memory) {
        return functionDelegateCall(target, data, "Address: low-level delegate call failed");
    }

    function functionDelegateCall(address target, bytes memory data, string memory errorMessage) internal returns (bytes memory) {
        require(isContract(target), "Address: delegate call to non-contract");

        // solhint-disable-next-line avoid-low-level-calls
        (bool success, bytes memory returndata) = target.delegatecall(data);
        return _verifyCallResult(success, returndata, errorMessage);
    }

    function _verifyCallResult(bool success, bytes memory returndata, string memory errorMessage) private pure returns(bytes memory) {
        if (success) {
            return returndata;
        } else {
            // Look for revert reason and bubble it up if present
            if (returndata.length > 0) {
                // The easiest way to bubble the revert reason is using memory via assembly

                // solhint-disable-next-line no-inline-assembly
                assembly {
                    let returndata_size := mload(returndata)
                    revert(add(32, returndata), returndata_size)
                }
            } else {
                revert(errorMessage);
            }
        }
    }
}

library SafeERC20 {
  function safeTransfer(
    IERC20 token,
    address to,
    uint256 value
  )
    internal
  {
    require(token.transfer(to, value));
  }

  function safeTransferFrom(
    IERC20 token,
    address from,
    address to,
    uint256 value
  )
    internal
  {
    require(token.transferFrom(from, to, value));
  }

  function safeApprove(
    IERC20 token,
    address spender,
    uint256 value
  )
    internal
  {
    require(token.approve(spender, value));
  }
}

interface IERC20{
    function totalSupply() external view returns (uint);
    function balanceOf(address account) external view returns (uint);
    function transfer(address recipient, uint amount) external returns (bool);
    function allowance(address owner, address spender) external view returns (uint);
    function approve(address spender, uint amount) external returns (bool);
    function transferFrom(address sender, address recipient, uint amount) external returns (bool);
}

interface ERC721TokenReceiver{
  function onERC721Received(
    address _operator,
    address _from,
    uint256 _tokenId,
    bytes calldata _data
  )
    external
    returns(bytes4);
}

contract Ownable
{

  /**
   * @dev Error constants.
   */
  string public constant NOT_CURRENT_OWNER = "018001";
  string public constant CANNOT_TRANSFER_TO_ZERO_ADDRESS = "018002";
  string public constant NOT_CURRENT_MANAGER = "018003";

  address public owner;
  mapping(address=>bool) public Manager;


  event OwnershipTransferred(
    address indexed previousOwner,
    address indexed newOwner
  );


  constructor()
    public
  {
    owner = msg.sender;
  }
  modifier onlyOwner()
  {
    require(msg.sender == owner, NOT_CURRENT_OWNER);
    _;
  }
  
  modifier onlyManager()    
  {
    require(Manager[msg.sender], NOT_CURRENT_MANAGER);
    _;
  }

  function addManager(address _maddr) public onlyOwner{
      Manager[_maddr] = true;
  }
  
  function delManager(address _maddr) public onlyOwner{
      Manager[_maddr] = false;
  }
  function transferOwnership(
    address _newOwner
  )
    public
    onlyOwner
  {
    require(_newOwner != address(0), CANNOT_TRANSFER_TO_ZERO_ADDRESS);
    emit OwnershipTransferred(owner, _newOwner);
    owner = _newOwner;
  }

}


contract NFToken is
  ERC721
{
  using SafeMath for uint256;
  using Address for address;

 
  string constant ZERO_ADDRESS = "003001";
  string constant NOT_VALID_NFT = "003002";
  string constant NOT_OWNER_OR_OPERATOR = "003003";
  string constant NOT_OWNER_APPROWED_OR_OPERATOR = "003004";
  string constant NOT_ABLE_TO_RECEIVE_NFT = "003005";
  string constant NFT_ALREADY_EXISTS = "003006";
  string constant NOT_OWNER = "003007";
  string constant IS_OWNER = "003008";


  bytes4 internal constant MAGIC_ON_ERC721_RECEIVED = 0x150b7a02;

  
  mapping (uint256 => address) public idToOwner;

  uint256 internal tokenID;
  mapping (uint256 => address) internal idToApproval;

   
  mapping (address => uint256) private ownerToNFTokenCount;

  
  mapping (address => mapping (address => bool)) internal ownerToOperators;

  
  event Transfer(
    address indexed _from,
    address indexed _to,
    uint256 indexed _tokenId
  );

 
  event Approval(
    address indexed _owner,
    address indexed _approved,
    uint256 indexed _tokenId
  );
  

 
  event ApprovalForAll(
    address indexed _owner,
    address indexed _operator,
    bool _approved
  );


  modifier canOperate(
    uint256 _tokenId
  )
  {
    address tokenOwner = idToOwner[_tokenId];
    require(tokenOwner == msg.sender || ownerToOperators[tokenOwner][msg.sender], NOT_OWNER_OR_OPERATOR);
    _;
  }


  modifier canTransfer(
    uint256 _tokenId
  )
  {
    address tokenOwner = idToOwner[_tokenId];
    require(
      tokenOwner == msg.sender
      || idToApproval[_tokenId] == msg.sender
      || ownerToOperators[tokenOwner][msg.sender],
      NOT_OWNER_APPROWED_OR_OPERATOR
    );
    _;
  }


  modifier validNFToken(
    uint256 _tokenId
  )
  {
    require(idToOwner[_tokenId] != address(0), NOT_VALID_NFT);
    _;
  }


  constructor()
    public
  {
    //supportedInterfaces[0x80ac58cd] = true; // ERC721
  }
  
  function viewTokenID() view public returns(uint256 ){
      return tokenID;
  }
  
  function safeTransferFrom(
    address _from,
    address _to,
    uint256 _tokenId,
    bytes calldata _data
  )
    external
    //override
  {
    _safeTransferFrom(_from, _to, _tokenId, _data);
  }
  


  function safeTransferFrom(
    address _from,
    address _to,
    uint256 _tokenId
  )
    external
    //override
  {
    _safeTransferFrom(_from, _to, _tokenId, "");
  }

 
  function transferFrom(
    address _from,
    address _to,
    uint256 _tokenId
  )
    external
    //override
    canTransfer(_tokenId)
    validNFToken(_tokenId)
  {
    address tokenOwner = idToOwner[_tokenId];
    require(tokenOwner == _from, NOT_OWNER);
    require(_to != address(0), ZERO_ADDRESS);

    _transfer(_to, _tokenId);
  }
  
  function transferList(address _to,uint256[] calldata _tokenIdList) external{
        uint256 len = _tokenIdList.length;
        address tokenOwner;// = idToOwner[_tokenId];
        uint256 _tokenId;
        
        for(uint256 i=0;i<len;i++){
            _tokenId = _tokenIdList[i];
            tokenOwner = idToOwner[_tokenId];
            require(tokenOwner != address(0), NOT_VALID_NFT);
            require(
                tokenOwner == msg.sender
                || idToApproval[_tokenId] == msg.sender
                || ownerToOperators[tokenOwner][msg.sender],
                NOT_OWNER_APPROWED_OR_OPERATOR
            );
            _transfer(_to, _tokenId);
        }
  }

  function approve(
    address _approved,
    uint256 _tokenId
  )
    external
    //override
    canOperate(_tokenId)
    validNFToken(_tokenId)
  {
    address tokenOwner = idToOwner[_tokenId];
    require(_approved != tokenOwner, IS_OWNER);

    idToApproval[_tokenId] = _approved;
    emit Approval(tokenOwner, _approved, _tokenId);
  }

 
  function setApprovalForAll(
    address _operator,
    bool _approved
  )
    external
    //override
  {
    ownerToOperators[msg.sender][_operator] = _approved;
    emit ApprovalForAll(msg.sender, _operator, _approved);
  }

 
  function balanceOf(
    address _owner
  )
    external
    //override
    view
    returns (uint256)
  {
    require(_owner != address(0), ZERO_ADDRESS);
    return _getOwnerNFTCount(_owner);
  }

 
  function ownerOf(
    uint256 _tokenId
  )
    external
    //override
    view
    returns (address _owner)
  {
    _owner = idToOwner[_tokenId];
    require(_owner != address(0), NOT_VALID_NFT);
  }


  function getApproved(
    uint256 _tokenId
  )
    external
    //override
    view
    validNFToken(_tokenId)
    returns (address)
  {
    return idToApproval[_tokenId];
  }


  function isApprovedForAll(
    address _owner,
    address _operator
  )
    external
    //override
    view
    returns (bool)
  {
    return ownerToOperators[_owner][_operator];
  }

  function _transfer(
    address _to,
    uint256 _tokenId
  )
    internal
  {
    address from = idToOwner[_tokenId];
    _clearApproval(_tokenId);

    _removeNFToken(from, _tokenId);
    _addNFToken(_to, _tokenId);

    emit Transfer(from, _to, _tokenId);
  }


  function _mint(
    address _to,
    uint256 _tokenId
  )
    internal
    //virtual
  {
    require(_to != address(0), ZERO_ADDRESS);
    require(idToOwner[_tokenId] == address(0), NFT_ALREADY_EXISTS);
    //require(_tokenId == tokenID+1,NFT_ALREADY_EXISTS);
    tokenID++;
    _addNFToken(_to, _tokenId);

    emit Transfer(address(0), _to, _tokenId);
  }


  function _burn(
    uint256 _tokenId
  )
    internal
    //virtual
    validNFToken(_tokenId)
  {
    address tokenOwner = idToOwner[_tokenId];
    _clearApproval(_tokenId);
    _removeNFToken(tokenOwner, _tokenId);
    emit Transfer(tokenOwner, address(0), _tokenId);
  }

 
  function _removeNFToken(
    address _from,
    uint256 _tokenId
  )
    internal
    //virtual
  {
    require(idToOwner[_tokenId] == _from, NOT_OWNER);
    ownerToNFTokenCount[_from] = ownerToNFTokenCount[_from] - 1;
    delete idToOwner[_tokenId];
  }

  
  function _addNFToken(
    address _to,
    uint256 _tokenId
  )
    internal
    //virtual
  {
    require(idToOwner[_tokenId] == address(0), NFT_ALREADY_EXISTS);

    idToOwner[_tokenId] = _to;
    ownerToNFTokenCount[_to] = ownerToNFTokenCount[_to].add(1);
  }


  function _getOwnerNFTCount(
    address _owner
  )
    internal
    //virtual
    view
    returns (uint256)
  {
    return ownerToNFTokenCount[_owner];
  }



  function _safeTransferFrom(
    address _from,
    address _to,
    uint256 _tokenId,
    bytes memory _data
  )
    private
    canTransfer(_tokenId)
    validNFToken(_tokenId)
  {
    address tokenOwner = idToOwner[_tokenId];
    require(tokenOwner == _from, NOT_OWNER);
    require(_to != address(0), ZERO_ADDRESS);

    _transfer(_to, _tokenId);

    if (_to.isContract())
    {
      bytes4 retval = ERC721TokenReceiver(_to).onERC721Received(msg.sender, _from, _tokenId, _data);
      require(retval == MAGIC_ON_ERC721_RECEIVED, NOT_ABLE_TO_RECEIVE_NFT);
    }
  }

  function _clearApproval(
    uint256 _tokenId
  )
    private
  {
    if (idToApproval[_tokenId] != address(0))
    {
      delete idToApproval[_tokenId];
    }
  }

}

contract KIMdate is NFToken {
    using SafeMath for uint256;
    string internal nftName;
    string internal nftSymbol;
    enum starType {st_nil,st1,st2,st3,st4,st5,st6,st7,st8,st9,st10,st11,st12,st13,st14,st15,st16,st17,st18,st19,st20}
    enum teamType {t_nil,t1,t2,t3,t4,t5,t6,t7,t8}

    string constant INVALID_INDEX = "005007";

    uint256[] public tokens;
    mapping(uint256 => uint256) public idToIndex;
    mapping(address => uint256[]) public ownerToIds;
    mapping(uint256 => uint256) public idToOwnerIndex;

    mapping (uint256 => string) public idToUri;

    mapping (uint256 => bool) public old_number;
    mapping(uint256 => starAttributesStruct) public starAttributes;

    //weapon
    mapping(address=>mapping(uint256=>uint256)) public weaponBalance;//ply -> weapon->number
    mapping(uint256 => uint256) public weaponTotal;
    mapping(address =>mapping(address =>mapping(uint256=>uint256))) public weaponApprove;
    mapping(uint256 => uint8) public weaponTeam; //weaponTeam


    event ADD_DEL_ToNFT(uint256 _tokenId,uint256 _newPower,bool isInstall );

    struct starAttributesStruct{
      teamType  ttype;//Team1 ,team2
      starType stype;//ABCDEF
      uint256 power; // power
      uint256 tpye1; // weapon
      uint256 tpye2; // picture;
      uint256 weapon_tokenId;
      uint256 weapon_power;
      uint256 defaultpower;
      uint256 tpye6;
      uint256 tpye7;
      uint256 isWeaponInUse;
      bool isCard;
    }

    function indexToID(uint256 _index) public view returns (uint256){
        return tokens[_index];
    }
    
    function installWeaponToNFT(uint256 _tokenId,uint256 _weapon_tokenId) public validNFToken(_tokenId) validNFToken(_weapon_tokenId) canTransfer(_tokenId) canTransfer(_weapon_tokenId){
        require(starAttributes[_tokenId].isCard,"Need Card");
        require(starAttributes[_tokenId].weapon_tokenId == 0,"Only One Skill Per Card");
        require(!starAttributes[_weapon_tokenId].isCard,"Need Skill");
        require(starAttributes[_weapon_tokenId].isWeaponInUse == 0,"Need Unused Skill");
        // set card weapon/skill
        _setTokenTypeAttributes(_tokenId,4,_weapon_tokenId);
        // calc new card power
        uint256 _defaultpower = starAttributes[_tokenId].defaultpower;
        uint256 _newPower_add_on = starAttributes[_weapon_tokenId].weapon_power.mul(_defaultpower).div(100);
        uint256 _newPower = _defaultpower.add(_newPower_add_on);
        // set new card power
        _setTokenTypeAttributes(_tokenId,3,_newPower);
        // set skill as used
        _setTokenTypeAttributes(_weapon_tokenId,8,1);
        emit ADD_DEL_ToNFT( _tokenId, _newPower, true );
    }

    function unInstalWeaponToNFT(uint256 _tokenId ) public validNFToken(_tokenId) canTransfer(_tokenId){
        require(starAttributes[_tokenId].isCard,"Need Card");
        uint256 _weapon_tokenId = starAttributes[_tokenId].weapon_tokenId;
        require(_weapon_tokenId != 0, "Card need to have skill");
        require(idToOwner[_weapon_tokenId] == msg.sender,"Need own weapon");
        //set card power to default
        uint256 _defaultpower = starAttributes[_tokenId].defaultpower;
        _setTokenTypeAttributes(_tokenId,3,_defaultpower);
        //set card weapon_tokenId to 0
        _setTokenTypeAttributes(_tokenId,4,0);
        //set weapon/skill as Unused
        _setTokenTypeAttributes(_weapon_tokenId,8,0);
        emit ADD_DEL_ToNFT( _tokenId, _defaultpower, false );
    }

    function totalSupply() external view returns (uint256) {
        return tokens.length;
    }

    function tokenByIndex(uint256 _index) external view returns (uint256){
        require(_index < tokens.length, INVALID_INDEX);
        return tokens[_index];
    }

    function tokenOfOwnerByIndex(address _owner,uint256 _index) external view returns (uint256){
        require(_index < ownerToIds[_owner].length, INVALID_INDEX);
        return ownerToIds[_owner][_index];
    }

    function _mint(address _to,uint256 _tokenId) internal {
        super._mint(_to, _tokenId);
        tokens.push(_tokenId);
        idToIndex[_tokenId] = tokens.length - 1;

    }
    
    // /function multiApprove(address )

    function _burn(uint256 _tokenId) internal {
        super._burn(_tokenId);

        uint256 tokenIndex = idToIndex[_tokenId];
        uint256 lastTokenIndex = tokens.length - 1;
        uint256 lastToken = tokens[lastTokenIndex];

        tokens[tokenIndex] = lastToken;

        tokens.pop();
        // This wastes gas if you are burning the last token but saves a little gas if you are not.
        idToIndex[lastToken] = tokenIndex;
        idToIndex[_tokenId] = 0;
    }


    function _removeNFToken(address _from,uint256 _tokenId) internal {
        require(idToOwner[_tokenId] == _from, NOT_OWNER);
        delete idToOwner[_tokenId];

        uint256 tokenToRemoveIndex = idToOwnerIndex[_tokenId];
        uint256 lastTokenIndex = ownerToIds[_from].length - 1;

        if (lastTokenIndex != tokenToRemoveIndex){
            uint256 lastToken = ownerToIds[_from][lastTokenIndex];
            ownerToIds[_from][tokenToRemoveIndex] = lastToken;
            idToOwnerIndex[lastToken] = tokenToRemoveIndex;
        }

        ownerToIds[_from].pop();
    }


    function _addNFToken(address _to,uint256 _tokenId) internal {
        require(idToOwner[_tokenId] == address(0), NFT_ALREADY_EXISTS);
        idToOwner[_tokenId] = _to;

        ownerToIds[_to].push(_tokenId);
        idToOwnerIndex[_tokenId] = ownerToIds[_to].length - 1;
    }

    function _getOwnerNFTCount(address _owner) internal view returns (uint256){
        return ownerToIds[_owner].length;

    }

    function _setTokenUri(uint256 _tokenId, string memory _uri) public validNFToken(_tokenId) {
        idToUri[_tokenId] = _uri;
    }


    function _setTokenAttributes(uint256 _tokenId, starType  _stype, teamType _ttpye,uint256 _power,bool _isCard)
    public validNFToken(_tokenId) {
        require(_stype > starType.st_nil && _stype <= starType.st20);
        require(_ttpye > teamType.t_nil && _ttpye <= teamType.t2);
        if(_isCard){
            starAttributes[_tokenId] =  starAttributesStruct(_ttpye,_stype,_power,0,0,0,0,_power,0,0,0,_isCard);
        }
        else{
            starAttributes[_tokenId] =  starAttributesStruct(_ttpye,_stype,0,0,0,0,_power,0,0,0,0,_isCard);
        }
    }

    function _setTokenAttributes(uint256[] memory _tokenId, starType[] memory  _stype, teamType[] memory _ttpye,uint256[] memory _power,bool _isCard)
    public  {
       for(uint256 i=0;i<_tokenId.length;i++){
           _setTokenAttributes(_tokenId[i],_stype[i],_ttpye[i],_power[i],_isCard);
       }
    }

    function _setTokenTypeAttributes(uint256 _tokenId,uint8 _typeAttributes,uint256 _tvalue)
    internal validNFToken(_tokenId) {
        if(_typeAttributes == 1){
            starAttributes[_tokenId].tpye1 = _tvalue;
        }else if(_typeAttributes == 2){
            starAttributes[_tokenId].tpye2 = _tvalue; // picture;
        }else if(_typeAttributes == 3){
            starAttributes[_tokenId].power = _tvalue;
        }else if(_typeAttributes == 4){
            starAttributes[_tokenId].weapon_tokenId= _tvalue;
        }else if(_typeAttributes == 5){
            starAttributes[_tokenId].defaultpower = _tvalue;
        }else if(_typeAttributes == 6){
            starAttributes[_tokenId].tpye6 = _tvalue;
        }else if(_typeAttributes == 7){
            starAttributes[_tokenId].tpye7 = _tvalue;
        }else if(_typeAttributes == 8){
            starAttributes[_tokenId].isWeaponInUse = _tvalue;
        }
    }

}


interface getCard{
    function drawCard() external returns(uint,string memory,uint,bool);
    function upCard(uint,uint,bool) external returns(uint,string memory,uint,bool);
}

contract KI_NFT is KIMdate,Ownable{
    uint256 tid = 0;
    using SafeMath for uint256;
    using SafeERC20 for IERC20;
    address payable internal cardfeeaddress = 0xbc0107abe29099673CC35b8b8b31cb3aC0Ea0e11;
    address payable internal updateFeeaddress = 0xbc039510075865b064A6223540459B6CDb16e569;
    address payable internal USDT_TOKEN = 0x55d398326f99059fF775485246999027B3197955;
    address public cardaddr = 0x165A306e97F5335c580FbccE9448137BE60912DA;
    address payable internal KI_TOKEN;
    uint256 public itemUnitPrice = 20 *10**18;
    uint256 public updateFee = 200 *10**18;
    uint256 public KiUintPrice = 20 *10**18;
    

    constructor(string memory _name,string memory _symbol) public {
        nftName = _name;
        nftSymbol = _symbol;
    }

    event Transfer(
      address indexed _from,
      address indexed _to,
      uint256 indexed numTokens
    );


    event Approval(
      address indexed _from,
      address indexed _to,
      uint256 indexed numTokens
    );

    function mint(
        address _to,
        uint256 _power,
        uint _stype,
        string calldata _url,
        bool _isCard
    )
        external
        onlyManager
    {
        tid++;
        super._mint(_to,tid);
        super._setTokenUri(tid, _url);
        super._setTokenAttributes(tid,starType(_stype),teamType.t1,_power,_isCard);
    }

    function burn(uint256 _tokenId) external onlyManager {
        super._burn(_tokenId);
    }
    
    function setTokenTypeAttributes(uint256 _tokenId,uint8 _typeAttributes,uint256 _tvalue) external onlyManager{
        super._setTokenTypeAttributes(_tokenId,_typeAttributes,_tvalue);
    }
    
    function setUpdateFee(uint256 _UpdateFee) external onlyManager {
        updateFee = _UpdateFee;
    }
    
    function setKiUnitPrice(uint256 _KiUintPrice) external onlyManager {
        KiUintPrice = _KiUintPrice;
    }
    
    function setcardfeeaddr(address payable _cardaddr) external onlyManager {
        cardfeeaddress = _cardaddr;
    }
    
    
    function setcardpooladdr(address _cardfee) external onlyManager {
        cardaddr = _cardfee;
    }
    
    function setkiaddr(address payable _kiaddr) external onlyManager {
        KI_TOKEN = _kiaddr;
    }
    
    function setItemUnitPrice(uint256 _unitprice) external onlyManager {
        itemUnitPrice = _unitprice;
    }
    function setTidIndex(uint256 _tid) external onlyManager {
        tid = _tid;
    }
    
    function upnft(uint256[] memory cardIds) public returns (bool){
        IERC20 token = IERC20(KI_TOKEN);
        token.transferFrom(msg.sender,updateFeeaddress,updateFee);
        emit Transfer(msg.sender, updateFeeaddress, updateFee);
        uint256 cardpower = starAttributes[cardIds[0]].power;
        uint256 stype  = uint(starAttributes[cardIds[0]].stype);
        bool isSameStype = true;
        for (uint8 i = 0; i < cardIds.length; i++) {
            require(starAttributes[cardIds[i]].power < 40000, "could not update ur");
            require(starAttributes[cardIds[i]].weapon_tokenId == 0, 'could not upgrade card with weapon');
            require(cardIds.length == 4, 'only  4 cards');
            require(msg.sender == idToOwner[cardIds[i]], 'not card owner');
            require(starAttributes[cardIds[i]].power == cardpower, 'not same level');
            if (uint(starAttributes[cardIds[i]].stype) != stype){
                isSameStype = false;
            }
        }
        
        for (uint8 i = 0; i < cardIds.length; i++) {
            super._burn(cardIds[i]);
        }
        //create new card
        tid ++;
        getCard r = getCard(cardaddr);
        (uint256 _power, string memory _url,uint _stype, bool _isCard)  = r.upCard(cardpower,stype,isSameStype);
        super._mint(msg.sender,tid);
        super._setTokenUri(tid, _url);
        super._setTokenAttributes(tid,starType(_stype),teamType.t1,_power,_isCard);
        return true;
    }

    function getNFTbyUSDT(uint256 _item_amount) public returns(bool){
        IERC20 token = IERC20(USDT_TOKEN);
        getCard r = getCard(cardaddr);
        uint256 numTokens = itemUnitPrice.mul(_item_amount);
        token.transferFrom(msg.sender,cardfeeaddress,numTokens);
        emit Transfer(msg.sender, cardfeeaddress, numTokens);
        if (_item_amount == 30){
            _item_amount = _item_amount + 5;
        }
        if (_item_amount == 10){
            _item_amount ++;
        }
        for (uint i=0; i<_item_amount; i++){
            (uint256 _power, string memory _url,uint _stype, bool _isCard)  = r.drawCard();
            tid ++;
            if(_isCard){
                super._mint(msg.sender,tid);
                super._setTokenUri(tid, _url);
                super._setTokenAttributes(tokenID,starType(_stype),teamType.t1,_power,_isCard);
            }
            else{
                super._mint(msg.sender,tid);
                super._setTokenUri(tid, _url);
                super._setTokenAttributes(tid,starType(_stype),teamType.t1,_power,_isCard);
            }
        }
        return true;
    }

    function getNFTbyKI(uint256 _item_amount) public returns(bool){
        IERC20 token = IERC20(KI_TOKEN);
        getCard r = getCard(cardaddr);
        uint256 numTokens = itemUnitPrice.mul(_item_amount);
        token.transferFrom(msg.sender,cardfeeaddress,numTokens);
        emit Transfer(msg.sender, cardfeeaddress, numTokens);
        if (_item_amount == 30){
            _item_amount = _item_amount + 5;
        }
        if (_item_amount == 10){
            _item_amount ++;
        }
        for (uint i=0; i<_item_amount; i++){
            (uint256 _power, string memory _url,uint _stype, bool _isCard)  = r.drawCard();
            tid ++;
            if(_isCard){
                super._mint(msg.sender,tid);
                super._setTokenUri(tid, _url);
                super._setTokenAttributes(tokenID,starType(_stype),teamType.t1,_power,_isCard);
            }
            else{
                super._mint(msg.sender,tid);
                super._setTokenUri(tid, _url);
                super._setTokenAttributes(tid,starType(_stype),teamType.t1,_power,_isCard);
            }
        }
        return true;
    }
}