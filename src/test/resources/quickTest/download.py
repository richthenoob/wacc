import requests
import glob
import json
import os

# Call reference compiler with AST flag
url = "https://teaching.doc.ic.ac.uk/wacc_compiler/run.cgi"
data = {"options[]": ["-a", "-x"]}

# Iterate through all files in folder
for filepath in glob.glob("../wacc_examples/valid/**/*.wacc", recursive=True):
    savepath = os.path.join(*(filepath.split(os.path.sep)[2:])) + "asm"
    file_contents = open(filepath, "r").read()

    # Only POST to refCompiler if file doesn't exist
    if not os.path.isfile(savepath):
        print(savepath)

        savedir, _ = os.path.split(savepath)
        if not os.path.isdir(savedir):
            os.makedirs(savedir)

        with open(filepath, "r") as input_file:
            r = requests.post(url, files={"testfile": input_file}, data=data)
            compiler_out = r.json()["compiler_out"]

            with open(savepath, "w") as output_file:
                output_file.write(file_contents + "\n" + compiler_out)