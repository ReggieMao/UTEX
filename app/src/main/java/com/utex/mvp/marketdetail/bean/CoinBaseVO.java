package com.utex.mvp.marketdetail.bean;

/**
 * Created by Demon on 2018/7/3.
 */
public class CoinBaseVO {

    /**
     * data : {"amount_decimal":"8","block_explorer":"https://etherscan.io/tx/{}","circulation":"99717772","circulation_value":"","coin_alias_code":"eth","coin_code":"eth","coin_en_name":"ethereum","coin_introduction":"<p>Ethereum（以太坊）是一个平台和一种编程语言，使开发人员能够建立和发布下一代分布式应用。 Ethereum可以用来编程，分散，担保和交易任何事物：投票，域名，金融交易所，众筹，公司管理， 合同和大部分的协议，知识产权，还有得益于硬件集成的智能资产。<\/p><p>以太坊将使用混合型的安全协议，前期使用工作量证明机制（POW），用于分发以太币，然后会切换到权益证明机制（POS）。自上线时起，每年都将有0.26x，即每年有60102216 * 0.26 = 15626576个以太币被矿工挖出。转成POS后，每年产出的以太币将减少<\/p>","coin_logo":"https://exnow.oss-cn-hongkong.aliyuncs.com/coinLogo/1527511110365.png","coin_name":"以太坊","contract_address":"","create_time":1527511489000,"deposit_fee":0,"digit_limit":1.0E-4,"id":2,"issue_time":1406160000000,"length":42,"need_tag":0,"official_website":"https://www.ethereum.org/","open_type":1,"platform":"","save_decimal":16,"show_decimal":8,"status":1,"token_price":1.89,"total_amount":"","white_paper":"https://github.com/ethereum/wiki/wiki/%5BEnglish%5D-White-Paper","withdrawal_fee":5.0E-4,"withdrawal_min_amount":0.005}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * amount_decimal : 8
         * block_explorer : https://etherscan.io/tx/{}
         * circulation : 99717772
         * circulation_value :
         * coin_alias_code : eth
         * coin_code : eth
         * coin_en_name : ethereum
         * coin_introduction : <p>Ethereum（以太坊）是一个平台和一种编程语言，使开发人员能够建立和发布下一代分布式应用。 Ethereum可以用来编程，分散，担保和交易任何事物：投票，域名，金融交易所，众筹，公司管理， 合同和大部分的协议，知识产权，还有得益于硬件集成的智能资产。</p><p>以太坊将使用混合型的安全协议，前期使用工作量证明机制（POW），用于分发以太币，然后会切换到权益证明机制（POS）。自上线时起，每年都将有0.26x，即每年有60102216 * 0.26 = 15626576个以太币被矿工挖出。转成POS后，每年产出的以太币将减少</p>
         * coin_logo : https://exnow.oss-cn-hongkong.aliyuncs.com/coinLogo/1527511110365.png
         * coin_name : 以太坊
         * contract_address :
         * create_time : 1527511489000
         * deposit_fee : 0
         * digit_limit : 1.0E-4
         * id : 2
         * issue_time : 1406160000000
         * length : 42
         * need_tag : 0
         * official_website : https://www.ethereum.org/
         * open_type : 1
         * platform :
         * save_decimal : 16
         * show_decimal : 8
         * status : 1
         * token_price : 1.89
         * total_amount :
         * white_paper : https://github.com/ethereum/wiki/wiki/%5BEnglish%5D-White-Paper
         * withdrawal_fee : 5.0E-4
         * withdrawal_min_amount : 0.005
         */

        private String amount_decimal;
        private String block_explorer;
        private String circulation;
        private String circulation_value;
        private String coin_alias_code;
        private String coin_code;
        private String coin_en_name;
        private String coin_introduction;
        private String coin_logo;
        private String coin_name;
        private String contract_address;
        private long create_time;
        private int deposit_fee;
        private double digit_limit;
        private int id;
        private long issue_time;
        private int length;
        private int need_tag;
        private String official_website;
        private int open_type;
        private String platform;
        private int save_decimal;
        private int show_decimal;
        private int status;
        private double token_price;
        private String total_amount;
        private String white_paper;
        private double withdrawal_fee;
        private double withdrawal_min_amount;

        public String getAmount_decimal() {
            return amount_decimal;
        }

        public void setAmount_decimal(String amount_decimal) {
            this.amount_decimal = amount_decimal;
        }

        public String getBlock_explorer() {
            return block_explorer;
        }

        public void setBlock_explorer(String block_explorer) {
            this.block_explorer = block_explorer;
        }

        public String getCirculation() {
            return circulation;
        }

        public void setCirculation(String circulation) {
            this.circulation = circulation;
        }

        public String getCirculation_value() {
            return circulation_value;
        }

        public void setCirculation_value(String circulation_value) {
            this.circulation_value = circulation_value;
        }

        public String getCoin_alias_code() {
            return coin_alias_code;
        }

        public void setCoin_alias_code(String coin_alias_code) {
            this.coin_alias_code = coin_alias_code;
        }

        public String getCoin_code() {
            return coin_code;
        }

        public void setCoin_code(String coin_code) {
            this.coin_code = coin_code;
        }

        public String getCoin_en_name() {
            return coin_en_name;
        }

        public void setCoin_en_name(String coin_en_name) {
            this.coin_en_name = coin_en_name;
        }

        public String getCoin_introduction() {
            return coin_introduction;
        }

        public void setCoin_introduction(String coin_introduction) {
            this.coin_introduction = coin_introduction;
        }

        public String getCoin_logo() {
            return coin_logo;
        }

        public void setCoin_logo(String coin_logo) {
            this.coin_logo = coin_logo;
        }

        public String getCoin_name() {
            return coin_name;
        }

        public void setCoin_name(String coin_name) {
            this.coin_name = coin_name;
        }

        public String getContract_address() {
            return contract_address;
        }

        public void setContract_address(String contract_address) {
            this.contract_address = contract_address;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getDeposit_fee() {
            return deposit_fee;
        }

        public void setDeposit_fee(int deposit_fee) {
            this.deposit_fee = deposit_fee;
        }

        public double getDigit_limit() {
            return digit_limit;
        }

        public void setDigit_limit(double digit_limit) {
            this.digit_limit = digit_limit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getIssue_time() {
            return issue_time;
        }

        public void setIssue_time(long issue_time) {
            this.issue_time = issue_time;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getNeed_tag() {
            return need_tag;
        }

        public void setNeed_tag(int need_tag) {
            this.need_tag = need_tag;
        }

        public String getOfficial_website() {
            return official_website;
        }

        public void setOfficial_website(String official_website) {
            this.official_website = official_website;
        }

        public int getOpen_type() {
            return open_type;
        }

        public void setOpen_type(int open_type) {
            this.open_type = open_type;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public int getSave_decimal() {
            return save_decimal;
        }

        public void setSave_decimal(int save_decimal) {
            this.save_decimal = save_decimal;
        }

        public int getShow_decimal() {
            return show_decimal;
        }

        public void setShow_decimal(int show_decimal) {
            this.show_decimal = show_decimal;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getToken_price() {
            return token_price;
        }

        public void setToken_price(double token_price) {
            this.token_price = token_price;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getWhite_paper() {
            return white_paper;
        }

        public void setWhite_paper(String white_paper) {
            this.white_paper = white_paper;
        }

        public double getWithdrawal_fee() {
            return withdrawal_fee;
        }

        public void setWithdrawal_fee(double withdrawal_fee) {
            this.withdrawal_fee = withdrawal_fee;
        }

        public double getWithdrawal_min_amount() {
            return withdrawal_min_amount;
        }

        public void setWithdrawal_min_amount(double withdrawal_min_amount) {
            this.withdrawal_min_amount = withdrawal_min_amount;
        }
    }
}
