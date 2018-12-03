
fileContent = []

with open("../input/input.txt") as file:
    for line in file:
        boxString = line
        fileContent.append(boxString)

twoLetters = 0
threeLetters = 0

for boxString in fileContent:
    charDict = {}
    twoLettersFound = False
    threeLettersFound = False
    for char in boxString:
        charDict[char] = charDict.get(char, 0) + 1
    for value in charDict.values():
        if value == 2 and not twoLettersFound:
            twoLetters += 1
            twoLettersFound = True
        if value == 3 and not threeLettersFound:
            threeLetters += 1
            threeLettersFound = True

checksum = twoLetters * threeLetters
print("Checksum: %i" % checksum)

fileContent.sort()
for boxString in fileContent:
    currentString = boxString
    for compareWithString in fileContent:
        onlyOne = 0
        for index, charInLast in enumerate(compareWithString):
            if charInLast is not currentString[index]:
                onlyOne += 1
                markedIndex = index
            if onlyOne > 1:
                break
        if onlyOne == 1:
            print(currentString[:markedIndex] + currentString[markedIndex + 1:])
