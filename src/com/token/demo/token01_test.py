import base64

import requests

host = 'http://127.0.0.1:8080'
cpk = '123456'
appid = '62acf88c-55d0-465c-b08d-99cd36271231'


class Identify:
    cipher = None

    def get_secrit(self):
        url = f"{host}/api/ec/dev/auth/regist"

        payload = {}
        headers = {
            'appid': appid,
            'cpk': cpk,
        }

        response = requests.request("POST", url, headers=headers, data=payload)

        obj = response.json()
        self.secrit = obj.get('secrit')
        self.spk = obj.get('spk')
        self.public_key = self.spk

    def rsa_enc(self, data):
        cipher = self.get_cipher()
        encrypted_data = cipher.encrypt(data.encode())

        # 将加密后的数据转换为Base64格式
        enc = base64.b64encode(encrypted_data).decode()
        return enc

    def get_cipher(self, public_key=None):
        if public_key is None:
            public_key = self.public_key

        if self.cipher:
            return self.cipher
        from Cryptodome.PublicKey import RSA
        from Cryptodome.Cipher import PKCS1_v1_5
        cipher = PKCS1_v1_5.new(RSA.import_key(base64.b64decode(public_key)))
        self.cipher = cipher
        return cipher

    def get_token(self):
        secret = self.rsa_enc(self.secrit)

        url = f"{host}/api/ec/dev/auth/applytoken"
        payload = {}
        headers = {
            'appid': appid,
            'secret': secret
        }

        response = requests.post(url, headers=headers, data=payload)

        ob = response.json()
        self.token = ob.get('token')
        print(response.text)

    def userid_enc(self, userid):
        return self.rsa_enc(userid)

    def api_test(self):
        url = f"{host}/api/hrm/resource/add/getHrmResourceAddForm"
        url = f"{host}/api/demo01/getFormDemo"
        userid = '1'

        payload = {}
        headers = {
            'appid': appid,
            'token': self.token,
            'userid': self.userid_enc(userid),
        }

        response = requests.get(url, headers=headers, data=payload)

        print(response.text)


if __name__ == '__main__':
    identify = Identify()
    identify.get_secrit()
    identify.get_token()
    identify.api_test()
