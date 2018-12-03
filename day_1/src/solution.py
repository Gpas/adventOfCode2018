
sum = 0
frequenciesSeen = set()
frequencyFound = False
fileContent = []

with open("../input/input.txt") as file:
    for line in file:
        number = int(line)
        sum += number
        fileContent.append(number)

print("Sum: %i" % sum)

sum = 0

while not frequencyFound:
    for number in fileContent:
        sum += number
        if sum not in frequenciesSeen:
            frequenciesSeen.add(sum)
        elif not frequencyFound:
            print("First frequency twice: %i" % sum)
            frequencyFound = True
