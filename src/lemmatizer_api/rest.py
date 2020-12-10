from flask import Flask
from flask.json import jsonify
from flask_restful import Api, Resource
from preprocess.cleaner_imp import CleanerImp

import spacy
from spacy.cli import download

if not spacy.util.is_package("en_core_web_sm"):
        print(f"Missing ML models. Installing spacy en-core-web-sm model")
        download("en_core_web_sm")

app = Flask(name)
api = Api(app)

class Term(Resource):
    def get(self, word):
        termlist = []

        termlist.append(word)
        for word in word.split():
            termlist.append(word)

        cleaner = CleanerImp()
        lemmatized_words = [{term : cleaner.lemmatize(term)} for term in termlist]
        return jsonify(lemmatized_words)

api.add_resource(Term, '/term/<word>')

if name == 'main':
    app.run(host='0.0.0.0', port='8082')